package com.hcmute.backendtoeicapp.services;

import com.google.gson.Gson;
import com.hcmute.backendtoeicapp.AppConfiguration;
import com.hcmute.backendtoeicapp.base.BaseResponse;
import com.hcmute.backendtoeicapp.base.ErrorResponse;
import com.hcmute.backendtoeicapp.base.SuccessfulResponse;
import com.hcmute.backendtoeicapp.dto.PracticePartInfoResponse;
import com.hcmute.backendtoeicapp.entities.*;
import com.hcmute.backendtoeicapp.model.*;
import com.hcmute.backendtoeicapp.repositories.*;
import com.hcmute.backendtoeicapp.services.interfaces.ToeicAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ToeicAppServiceImpl implements ToeicAppService {
    @Autowired
    private ToeicPartRepository toeicPartRepository;
    @Autowired
    private ToeicQuestionGroupRepository toeicQuestionGroupRepository;
    @Autowired
    private ToeicQuestionRepository toeicQuestionRepository;
    @Autowired
    private ToeicItemContentRepository toeicItemContentRepository;
    @Autowired
    private ToeicAnswerChoiceRepository toeicAnswerChoiceRepository;

    @Autowired
    private AppConfiguration appConfiguration;

    @Override
    public BaseResponse getListPracticePartInfoByPartId(Integer id) {

        if (id < 1 || id > 7) {
            ErrorResponse response = new ErrorResponse();
            response.setMessage("Không tồn tại part id = " + id);
            return response;
        }
        List<PracticePartInfoResponse> practicePartInfoResponseList = new ArrayList<>();
        List<ToeicPartEntity> toeicPartEntityList = this.toeicPartRepository.getToeicPartByPartNumber(id);
        for (ToeicPartEntity toeicPartEntity : toeicPartEntityList) {
            PracticePartInfoResponse response = new PracticePartInfoResponse();
            response.setPartId(toeicPartEntity.getId());
            response.setNumOfQuestions(this.toeicPartRepository.getNumOfQuestions(toeicPartEntity.getId()));
            practicePartInfoResponseList.add(response);
        }
        for (int i = 0; i < practicePartInfoResponseList.size(); i++) {
            practicePartInfoResponseList.get(i).setName("Luyện tập part " + id + " - " + (i + 1));
        }
        SuccessfulResponse response = new SuccessfulResponse();
        response.setMessage("Lấy dữ liệu thành công");
        response.setData(practicePartInfoResponseList);
        return response;
    }

    private List<ToeicQuestionGroup> getListQuestionGroups(Integer partId) {
        List<ToeicQuestionGroupEntity> toeicQuestionGroupEntityList =
                this.toeicQuestionGroupRepository.getListToeicQuestionGroupByPartId(partId);
        List<ToeicQuestionGroup> questionGroups = new ArrayList<ToeicQuestionGroup>();
        for (ToeicQuestionGroupEntity entity : toeicQuestionGroupEntityList) {
            ToeicQuestionGroup model = new ToeicQuestionGroup();
            List<ToeicItemContentEntity> questionContentEntities =
                    this.toeicItemContentRepository.getListQuestionContentByQuestionGroupId(entity.getId());
            List<ToeicItemContentEntity> transcriptEntities =
                    this.toeicItemContentRepository.getListTranscriptByQuestionGroupId(entity.getId());
            List<ToeicQuestionEntity> questionEntities =
                    this.toeicQuestionRepository.getListToeicQuestionByQuestionGroupId(entity.getId());
            List<ToeicItemContent> questionContents = questionContentEntities.stream().map(ToeicItemContent::new).toList();
            List<ToeicItemContent> transcripts = transcriptEntities.stream().map(ToeicItemContent::new).toList();
            List<ToeicQuestion> questions = questionEntities.stream().map(ToeicQuestion::new).toList();
            for (int i = 0;i < questionEntities.size();i++) {
                List<ToeicAnswerChoiceEntity> answerChoiceEntities =
                        this.toeicAnswerChoiceRepository.getToeicAnswerChoiceByToeicQuestionId(questionEntities.get(i).getId());
                List<ToeicAnswerChoice> answerChoices = answerChoiceEntities.stream().map(ToeicAnswerChoice::new).toList();
                questions.get(i).setChoices(answerChoices);
            }
            model.setQuestionContent(questionContents);
            model.setTranscript(transcripts);
            model.setQuestions(questions);
            questionGroups.add(model);
        }
        return questionGroups;
    }

    @Override
    public BaseResponse getListQuestionGroupAndQuestionByPartId(Integer id) {
        if (this.toeicPartRepository.findToeicPartEntityById(id) == null) {
            ErrorResponse response = new ErrorResponse();
            response.setMessage("Không tồn tại part có id = " + id);
            return response;
        }

        List<ToeicQuestionGroup> questionGroups = this.getListQuestionGroups(id);

        SuccessfulResponse response = new SuccessfulResponse();
        response.setMessage("Lấy dữ liệu thành công");
        response.setData(questionGroups);
        return response;
    }

    private static final Pattern NON_LATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    public static String toSlug(String input) {
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NON_LATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }

    private String joinQuestionNumberFromGroupQuestion(Integer toeicGroupId) {
        final List<ToeicQuestionEntity> questions = this.toeicQuestionRepository.getListToeicQuestionByQuestionGroupId(toeicGroupId);
        assert questions.size() > 0;

        List<String> nums = questions.stream().map(question -> question.getQuestionId().toString()).toList();

        return String.join("-", nums);
    }

    @Override
    public byte[] downloadPartData(Integer partId) throws IOException {
        if (!this.toeicPartRepository.existsById(partId)) {
            return null;
        }

        final ToeicFullTestEntity toeicFullTest = this.toeicPartRepository.findById(partId).get().getToeicFullTestEntity();
        final String slug = toSlug(toeicFullTest.getFullName());

        final ByteArrayOutputStream inputStream = new ByteArrayOutputStream();
        final ZipOutputStream zipOutputStream = new ZipOutputStream(inputStream);

        for (ToeicQuestionGroupEntity toeicQuestionGroupEntity : this.toeicQuestionGroupRepository.getListToeicQuestionGroupByPartId(partId)) {

            final String joinedQuestionNumbers = this.joinQuestionNumberFromGroupQuestion(toeicQuestionGroupEntity.getId());
            final String inputFileNamePrrefix = Paths.get(
                    this.appConfiguration.getToeicResourceDirectory(),
                    slug + "-" + joinedQuestionNumbers
            ).toString();

            final List<String> suffixes = new ArrayList<>();
            suffixes.add(".mp3");
            if (partId == 1) {
                suffixes.add(".png");
            }

            for (final String suffix : suffixes) {
                final String inputFileName = inputFileNamePrrefix + suffix;
                final String outputFileName = joinedQuestionNumbers + suffix;

                File fileToZip = new File(inputFileName);
                final ZipEntry zipEntry = new ZipEntry(outputFileName);
                zipOutputStream.putNextEntry(zipEntry);

                if (fileToZip.exists())
                    Files.copy(fileToZip.toPath(), zipOutputStream);
            }
        }

        ZipEntry configEntry = new ZipEntry("config.json");
        zipOutputStream.putNextEntry(configEntry);
        Gson gson = new Gson();
        zipOutputStream.write(gson.toJson(this.getListQuestionGroups(partId)).getBytes(StandardCharsets.UTF_8));
        zipOutputStream.closeEntry();

        zipOutputStream.close();
        return inputStream.toByteArray();
    }
}
