package com.sm_academy.API;


import com.google.gson.JsonObject;
import com.sm_academy.ModelClass.AboutCourse.AboutCourseResponse;
import com.sm_academy.ModelClass.BatchDetailsResponse;
import com.sm_academy.ModelClass.BatchTabDetails.BatchResponse;
import com.sm_academy.ModelClass.BatchTimingResponse;
import com.sm_academy.ModelClass.CommonResponse;
import com.sm_academy.ModelClass.DashBoard.DashBoardResponse;
import com.sm_academy.ModelClass.DashBoard.DashBoardSectionResponse;
import com.sm_academy.ModelClass.OngoingLiveSeesion.OngoingLiveSessionResponse;
import com.sm_academy.ModelClass.PayementResponse;
import com.sm_academy.ModelClass.PracticeTest.AnswerKeyResponse;
import com.sm_academy.ModelClass.PracticeTest.MockTestCountResponse;
import com.sm_academy.ModelClass.PracticeTest.MockTestFullScoreResponse;
import com.sm_academy.ModelClass.PracticeTest.MockTestResultResponse;
import com.sm_academy.ModelClass.PracticeTest.MockTestStartResponse;
import com.sm_academy.ModelClass.PracticeTest.MockTestSubmitResponse;
import com.sm_academy.ModelClass.ProgressGraph.CourseResponse;
import com.sm_academy.ModelClass.ProgressGraph.CourseSummariesResponse;
import com.sm_academy.ModelClass.ProgressGraph.GraphMockTestSectionResponse;
import com.sm_academy.ModelClass.ProgressGraph.GraphResponse;
import com.sm_academy.ModelClass.ProgressGraph.NewBatchResponse;
import com.sm_academy.ModelClass.LiveSession.LiveSessionResponse;
import com.sm_academy.ModelClass.MainCoursesDetailsResponse;
import com.sm_academy.ModelClass.PTDetails.PTDetails;
import com.sm_academy.ModelClass.PaymentHistories.PaymentHistoriesResponse;
import com.sm_academy.ModelClass.PracticeTest.MockTestListResponse;
import com.sm_academy.ModelClass.PracticeTest.PracticeTestResult;
import com.sm_academy.ModelClass.PracticeTest.PracticeTestStartResponse;
import com.sm_academy.ModelClass.PracticeTest.PracticeTestStartWithDescription;
import com.sm_academy.ModelClass.PracticeTest.PracticeTestTopicWiseResponse;
import com.sm_academy.ModelClass.PracticeTest.PreacticeTestTopicsResponse;
import com.sm_academy.ModelClass.Profile.ProfileResponse;
import com.sm_academy.ModelClass.SigninResponse;
import com.sm_academy.ModelClass.SignupResponse;
import com.sm_academy.ModelClass.StatusResponse;
import com.sm_academy.ModelClass.CourseWithSyllabusDetailsResponse;
import com.sm_academy.ModelClass.StudyMaterialView.StudyMaterialsResponse;
import com.sm_academy.ModelClass.StudyMaterialsSectionTopics.SectionTopicResponse;
import com.sm_academy.ModelClass.StudyMaterialsSectionTopics.StudyMaterialsPhaseResponse;
import com.sm_academy.ModelClass.Subscription.SubscriptionResponse;


import org.json.JSONObject;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.QueryMap;
import retrofit2.Call;
import okhttp3.MultipartBody;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.Part;

/**
 * Created by Nikhil on 04-06-2016.
 */
public interface RetrofitAPIInterface {


    @Headers("Authorization: your_token_key_here")
    @Multipart
    @retrofit2.http.PUT("update_profile_picture")
    Call<ProfileResponse> uploadImage(@HeaderMap Map<String, String> token,
                                      @Part MultipartBody.Part file,
                                      @Part MultipartBody.Part file2,
                                      @Part MultipartBody.Part fil3,
                                      @Part MultipartBody.Part fil4,
                                      @Part MultipartBody.Part file5);

    @Headers("Authorization: your_token_key_here")
    @Multipart
    @retrofit2.http.PUT("update_profile_picture")
    Call<ProfileResponse> deleteImage(@HeaderMap Map<String, String> token,
                                      @Part MultipartBody.Part file,
                                      @Part MultipartBody.Part file2,
                                      @Part MultipartBody.Part fil3);

  /*  @Headers("Authorization: your_token_key_here")
    @Multipart
    @retrofit2.http.PUT("update_profile_picture")
    Call<ProfileResponse> uploadImage( @HeaderMap Map<String, String> token,
                                      @PartMap Map<String, String> map);*/

    @POST("/sign_in")
    @Headers({
            "Accept: application/json"
    })
    public void signIn(@Body JsonObject loginDetails, Callback<SigninResponse> status);

    @POST("/payment_histories")
    public void sendpayment(@Body JsonObject jsonObject, Callback<PayementResponse> jsonObjectCallback);

    @POST("/course_extension")
    public void sendSubscriptionpayment(@Body JsonObject jsonObject, Callback<PayementResponse> jsonObjectCallback);


    @DELETE("/sign_out")
    public void logout(Callback<JSONObject> logout);

    @GET("/get_courses")
    public void getCourseDetails(Callback<MainCoursesDetailsResponse> mainCoursesDetailsCallback);

    @GET("/show_batches")
    public void getbatchDetails(@QueryMap Map<String, String> params, Callback<BatchDetailsResponse> batchDetailsResponseCallback);

    @GET("/registrations/new")
    public void getRegistration(@QueryMap Map<String, String> params, Callback<SignupResponse> collegeInfoResponseCallback);

    @POST("/registrations")
    public void getOtpVarification(@Body JsonObject abc, Callback<SignupResponse> jsonObjectCallback);

    @GET("/show_batch_timings")
    public void getCalenderDetails(@QueryMap Map<String, String> params, Callback<BatchTimingResponse> batchTimingResponseCallback);

    @GET("/courses/get_course_details")
    public void getcourseWithSyllabusDetails(@QueryMap Map<String, String> params, Callback<CourseWithSyllabusDetailsResponse> studyMaterialDetailsResponseCallback);

    @GET("/show_live_sessions")
    public void getSessionDetails(Callback<LiveSessionResponse> liveSessionResponseCallback);

    @POST("/passwords")
    public void reset(@Body JsonObject abc, Callback<StatusResponse> jsonObjectCallback);

    @POST("/learners/update_learner")
    public void getadditionalDetails(@Body JsonObject jsonObject, Callback<StatusResponse> jsonObjectCallback);

    @GET("/proficiency_test")
    public void getptedetails(Callback<PTDetails> ptDetailsCallback);

    @POST("/submit_proficiency_test")
    public void submitPTETestDetails(@QueryMap Map<String, String> params, Callback<StatusResponse> jsonObjectCallback);

    @GET("/dashboard")
    public void getdashBoardDetails(Callback<DashBoardResponse> dashBoardResponseCallback);

    @GET("/dashboard")
    public void getSeconddashBoardDetails(Callback<JsonObject> jsonObjectCallback);

    @GET("/show_sections")
    public void getdashBoardSectionsDetails(@QueryMap Map<String, String> params, Callback<DashBoardSectionResponse> dashBoardSectionResponseCallback);

    @GET("/study_materials/get_section_topics")
    public void getstudyMaterialDetails(@QueryMap Map<String, String> params, Callback<SectionTopicResponse> sectionTopicResponseCallback);

    @GET("/study_materials/get_study_materials")
    public void getStudyMaterialsViewDetails(@QueryMap Map<String, String> params, Callback<StudyMaterialsResponse> studyMaterialsResponseCallback);

    @GET("/my_subscriptions")
    public void getsubscriptionDetails(Callback<SubscriptionResponse> subscriptionResponseCallback);

    @GET("/payment_histories")
    public void getpaymenthistoriesDetails(Callback<PaymentHistoriesResponse> paymentHistoriesResponseCallback);

    @PUT("/update_profile")
    public void getUpdateProfile(@Body JsonObject jsonObject, Callback<StatusResponse> profileResponseCallback);

    @GET("/profile")
    public void getProfile(Callback<ProfileResponse> profileResponseCallback);

    @PUT("/update_profile_picture")
    public void getdeletePicture(@Body JsonObject jsonObject
            , Callback<ProfileResponse> profileResponseCallback);

    @GET("/show_batches")
    public void getBatchTabDetails(@QueryMap Map<String, String> params, Callback<BatchResponse> batchResponseCallback);

    @GET("/show_course")
    public void getCourseFieldDetails(@QueryMap Map<String, String> params, Callback<AboutCourseResponse> aboutCourseResponseCallback);

    /*   @Multipart
       @PUT("/update_profile_picture")
       public void getProfilePicture(@Part MultipartBody.Part file,
                                     @Part MultipartBody.Part file2,
                                     @Part MultipartBody.Part fil3,
                                     @Part MultipartBody.Part fil4,
                                     @Part MultipartBody.Part file5
               , Callback<StatusResponse> profileResponseCallback);

   */
    /*
    @GET("/registrations/sign_out")
    public void logout(Map<String, String> params, Callback<JSONObject> logout);

    @GET("/indexes/college_info")
    public void getbatchDetails(Callback<CollegeInfoResponse> collegeInfoResponseCallback);

    @POST("/registrations/forgot_password")
    public void reset(@Body JsonObject abc, Callback<JsonObject> jsonObjectCallback);

    @GET("/employees/get_employee_details")
    public void getProfile(Callback<ProfileResponse> profileResponseCallback);

    @POST("/students/get_student_details")
    public void getStudentProfile(@Body JsonObject abc, Callback<ProfileResponse> profileResponseCallback);

    @GET("/indexes/profile_pic")
    public void getImage(Callback<ImageModel> imageModelCallback);

    @GET("/emails/user_emails")
    public void getComposedetails(Callback<ComposemailResponse> composemailResponseCallback);

    @POST("/emails/send_email")
    public void OnSend(@Body JsonObject message, Callback<StatusResponseClass> jsonObjectCallback);

    @GET("/emails/mail_box")
    public void getMaildetails(Callback<MailResponse> mailResponseCallback);

    @POST("/emails/sent_mail_delete")
    public void sentdeleteMail(@QueryMap Map<String, String> params, Callback<StatusResponseClass> todoJson);

    @POST("/emails/trash_mail_delete")
    public void trashdeleteMail(@QueryMap Map<String, String> params, Callback<StatusResponseClass> todoJson);

    @POST("/emails/move_to_trash")
    public void deleteMail(@QueryMap Map<String, String> params, Callback<StatusResponseClass> todoJson);

    @POST("/emails/email_detail_view")
    public void getReadMailDetails(@QueryMap Map<String, String> params, Callback<EmailReadResponse> collegeInfoCallback);

    @GET("/leave_applications/my_leave_applications")
    public void getLeaveDetails(Callback<LeaveResponse> leaveResponseCallback);

    @GET("/leave_applications")
    public void getEmployeeLeaveDetails(Callback<LeaveResponse> leaveResponseCallback);

    @POST("/leave_applications/apply_leave")
    public void applyLeave(@Body JsonObject abc, Callback<StatusResponseClass> jsonObjectCallback);

    @GET("/leave_applications/leave_types")
    public void getleaveType(Callback<LeaveTypeResponse> leaveResponseCallback);

    @POST("/leave_applications/update_leave")
    public void updateLeaves(@Body JsonObject abc, Callback<StatusResponseClass> leaveResponseCallback);

    @GET("/assignments/new")
    public void getSubjectType(Callback<SubjectResponseList> leaveResponseCallback);

    @GET("/time_tables/get_subject_types")
    public void getSubjectTypeApi(Callback<TimetableResponse> timetableResponseCallback);

    @POST("/assignments")
    public void submitAssignment(@Body JsonObject abc, Callback<JsonObject> leaveResponseCallback);

    @POST("/assignments/extend_date")
    public void getupdateDate(@Body JsonObject abc, Callback<StatusResponseClass> jsonObjectCallback);

    @POST("/assignments/marks")
    public void getupdateMark(@Body JsonObject abc, Callback<StatusResponseClass> jsonObjectCallback);

    @GET("/assignments")
    public void getActivedetails(Callback<AssignmentViewResponse> assignmentViewResponseCallback);

    @POST("/assignments/submission_pending_list")
    public void getUnsubmitActivedetails(@QueryMap Map<String, String> params, Callback<UnsubmitActiveResponse> collegeInfoCallback);

    @POST("/assignments/submitted_list")
    public void getAnswerActiveDetails(@QueryMap Map<String, String> params, Callback<AnswerActiveResponse> collegeInfoCallback);

    @POST("/assignments/update_assignment")
    public void updateAssignment(@Body JsonObject abc, Callback<StatusResponseClass> leaveResponseCallback);

    @GET("/indexes")
    public void getDashBoard(Callback<DashBoardResponse> dashBoardResponseCallback);

    @GET("/indexes")
    public void getStudentDashBoard(Callback<StudentDashboardResponse> studentDashboardResponseCallback);

    @GET("/indexes/semester_with_batch_and_department")
    public void getSemDetails(Callback<SemesterResponses> semesterResponseCallback);

    @GET("/students")
    public void getStudentDetails(@QueryMap Map<String, String> params, Callback<StudentListResponse> studentListResponseCallback);

    @POST("/exams/exam_index")
    public void getStudentExamListDetails(Callback<StudentExamListResponse> studentListResponseCallback);

    @POST("/marks/stud_view")
    public void getStudentExamMarkDetails(@Body JsonObject message, Callback<StudentExamViewMarkResponse> studentListResponseCallback);

    @GET("/tutors")
    public void getTutorStudentDetails(Callback<TutorStudentResponses> studentListResponseCallback);

    @POST("/tutors/send_circular")
    public void sendCircularDetails(@Body JsonObject message, Callback<StatusResponseClass> jsonObjectCallback);

    @POST("/tutors/circulars")
    public void getCircularViewDetails(@Body JsonObject message, Callback<CircularViewResponses> circularViewResponsesCallback);

    @POST("/attendances")
    public void getMarkAttendance(@Body JsonObject message, Callback<StatusResponseClass> jsonObjectCallback);

    @POST("/attendances/get_marked_details")
    public void getHodAttendance(@Body JsonObject message, Callback<HodAttendanceResponse> hodAttendanceResponseCallback);

    @POST("/subjects/get_timings")
    public void gettiming(@Body JsonObject message, Callback<TimingResponse> subjectResponseCallback);

    @POST("/employees/my_subjects")
    public void getSubject(@Body JsonObject message, Callback<SubjectResponse> subjectResponseCallback);

    @POST("/leave_applications/approve")
    public void approveLeave(@Body JsonObject message, Callback<StatusResponseClass> jsonObjectCallback);

    @POST("/leave_applications/reject")
    public void rejectLeave(@Body JsonObject message, Callback<StatusResponseClass> jsonObjectCallback);

    @POST("/leave_applications/destroy_leave")
    public void deleteLeave(@Body JsonObject message, Callback<StatusResponseClass> jsonObjectCallback);

    @POST("/time_tables/my_timetable")
    public void getTimetable(@Body JsonObject message, Callback<TimetableResponse> jsonObjectCallback);

    @POST("/hour_alterations/new_hour_alteration")
    public void getTimetableAltration(@Body JsonObject message, Callback<AltrationResponse> altrationResponseCallback);

    @POST("/substitutions/get_subjects")
    public void getAltrationSubject(@Body JsonObject message, Callback<AltrationSubjectResponse> subjectResponseCallback);

    @POST("/substitutions/get_employees")
    public void getAltrantionFaculy(@Body JsonObject message, Callback<AltrationEmployeeResponse> employeeResponseCallback);

    @POST("/substitutions")
    public void submitSubstituitons(@Body JsonObject message, Callback<StatusResponseClass> employeeResponseCallback);

    @GET("/substitutions")
    public void getaltrationdetails(Callback<TimeTableAlterationResponse> timeTableAlterationResponseCallback);

    @POST("/todo_lists")
    public void todoCreate(@Body JsonObject jsonObject, Callback<StatusResponseClass> todoJson);

    @POST("/assignments")
    public void applyAssignment(@Body JsonObject abc, Callback<StatusResponseClass> jsonObjectCallback);

    @GET("/assignments/new")
    public void getAssignmentSubject(Callback<SubjectResponse> subjectResponseCallback);

    @GET("/subjects/my_subjects")
    public void getStudentSubject(Callback<SubjectResponse> subjectResponseCallback);

    @POST("/attendances/my_attendance")
    public void getStudentAttendanceDetails(@Body JsonObject jsonObject, Callback<StudentAttendanceResponse> jsonObjectCallback);

    @POST("/syllabuses/units")
    public void getStudentSyllabusDetails(@Body JsonObject jsonObject, Callback<StudentSyllabusViewResponse> jsonObjectCallback);

    @POST("/syllabuses/sub_units")
    public void getSyllabusSubUntViewDetails(@Body JsonObject jsonObject, Callback<StudentSyllabusSubUnitResponse> jsonObjectCallback);

    @GET("/assignments")
    public void getassignmentdetails(Callback<AssignmentViewResponse> assignmentViewResponseCallback);

    @POST("/todo_lists/delete_todo_list")
    public void deleteMsg(@QueryMap Map<String, String> params, Callback<StatusResponseClass> todoJson);

    @POST("/todo_lists/update_todo_list_status")
    public void statusCreate(@Body JsonObject jsonObject, Callback<StatusResponseClass> todoJson);

    @GET("/todo_lists")
    public void empTodoView(Callback<TodoListResponse> todoListResponseCallback);

    @POST("/todo_lists/update_todo_list")
    public void todoUpdate(@Body JsonObject jsonObject, Callback<StatusResponseClass> todoJson);
    *//*  *//**//* @POST("/time_tables/pdf")
    public void downloadPdf(@Body JsonObject abc, Callback<JsonObject> jsonObjectCallback);*//**//*

    @POST("time_tables/pdf")
    Call<ResponseBody> downloadPdf(@Body JsonObject abc);*//*


    @GET("/indexes/notifications")
    public void getNotificationDetails(Callback<HodNotificationViewResponse> hodNotificationViewResponseCallback);

    @POST("/time_tables/pdf")
    Call<ResponseBody> downloadPdf(@Body JsonObject object);

    @GET("/indexes/get_branches")
    public void getBranch(Callback<BranchResponse> branchResponsehCallback);

    @POST("/indexes/get_batches")
    public void getBatch(@QueryMap Map<String, String> params, Callback<NewBatchResponse> jsonObjectCallback);

    @POST("/indexes/get_semesters")
    public void getSemester(@QueryMap Map<String, String> params, Callback<SemesterResponse> jsonObjectCallback);

    @POST("/indexes/get_sections")
    public void getSection(@QueryMap Map<String, String> params, Callback<SectionResponse> jsonObjectCallback);

    @GET("/exams/get_exam_types")
    public void getExam(Callback<ExamResponse> jsonObjectCallback);


    @POST("/exams")
    public void createExam(@Body JsonObject object, Callback<StatusResponseClass> jsonObjectCallback);

    @POST("/exams/exam_index")
    public void getExamView(@Body JsonObject object, Callback<ExamViewResponse> jsonObjectCallback);


    @POST("/exams/get_exams")
    public void getExamReport(@QueryMap Map<String, String> params, Callback<com.inmeghcms.exalogic.jdt_college_app.ModelsClass.HodModelClass.CommonModelClass.ExamReportResponse> jsonObjectCallback);

    @POST("/exams/exam_marks_report")
    public void getExamReportDetails(@Body JsonObject object, Callback<ExamReportResponse> jsonObjectCallback);

    @POST("/exams/exam_subject_analysis")
    public void createExamRange(@Body JsonObject object, Callback<ExamRangeResponse> jsonObjectCallback);

    @POST("/exams/get_exam_subjects")
    public void getSubjectView(@Body JsonObject object, Callback<SubjectResponses> jsonObjectCallback);

    @POST("/exams/subject_analysis")
    public void creteSubjectDetails(@Body JsonObject object, Callback<SubjectAnalysisViewResponse> jsonObjectCallback);

    @POST("/batches/get_students")
    public void getStudents(@QueryMap Map<String, String> params, Callback<StudentResponses> jsonObjectCallback);

    @POST("/exams/arrear_histories")
    public void getArrearExam(@Body JsonObject object, Callback<ArrearHistoriesResponse> jsonObjectCallback);

    @GET("/exams/exam_time_tables")
    public void getExamTimeTable(Callback<ExamTimeTableResponse> jsonObjectCallback);

    @POST("/exams/exam_schedule")
    public void getExamTimeTableView(@QueryMap Map<String, String> params, Callback<ExamTimeTableViewDetailsResponse> jsonObjectCallback);

    @POST("/marks/subject_wise_graph_analytics")
    public void subjectWiseViewGraph(@Body JsonObject object, Callback<SubjectWiseGraphResponse> jsonObjectCallback);

    @POST("/marks/subjects_graph_analytics")
    public void monthViewGraph(@Body JsonObject object, Callback<MonthlyViewGraphResponse> jsonObjectCallback);

    @POST("/directories/students_directory")
    public void getStudentSmsDetails(@Body JsonObject object, Callback<StudentSmsViewResponse> studentSmsViewResponseCallback);

    @POST("/directories/parents_directory")
    public void getParentSmsDetails(@Body JsonObject object, Callback<StudentSmsViewResponse> studentSmsViewResponseCallback);

    @POST("/directories/employees_directory")
    public void getEmployeeSmsDetails(@Body JsonObject object, Callback<EmployeeSmsViewResponse> employeeSmsViewResponseCallback);

    @POST("/sent_messages/sending_messages_for_students")
    public void getStudentSendSmsDetails(@Body JsonObject object, Callback<StatusResponseClass> jsonObjectCallback);

    @POST("/sent_messages/sending_messages_for_parents")
    public void getParentSendSmsDetails(@Body JsonObject object, Callback<StatusResponseClass> jsonObjectCallback);

    @POST("/sent_messages/sending_messages_for_employees")
    public void getEmployeeSendSmsDetails(@Body JsonObject object, Callback<StatusResponseClass> jsonObjectCallback);
*/
    @GET("/practice_tests/practice_test_topics")
    public void getPreacticeTestTopicsResponse(@QueryMap Map<String, String> params, Callback<PreacticeTestTopicsResponse> jsonObjectCallback);

    @GET("/assignments/assignment_topics")
    public void getAssignmentTopicsResponse(@QueryMap Map<String, String> params, Callback<PreacticeTestTopicsResponse> jsonObjectCallback);

    @GET("/assignments/topic_assignments")
    public void getAssignmentTopicWiseResponse(@QueryMap Map<String, String> params, Callback<PracticeTestTopicWiseResponse> jsonObjectCallback);

    @GET("/practice_tests/topic_practice_tests")
    public void getPracticeTestTopicWiseResponse(@QueryMap Map<String, String> params, Callback<PracticeTestTopicWiseResponse> jsonObjectCallback);

    @GET("/practice_tests/take_test")
    public void getPracticeTeststartdetails(@QueryMap Map<String, String> params, Callback<PracticeTestStartWithDescription> jsonObjectCallback);

    @GET("/assignments/take_test")
    public void getAssignmentTeststartdetails(@QueryMap Map<String, String> params, Callback<PracticeTestStartWithDescription> jsonObjectCallback);

    @GET("/mock_tests/take_test")
    public void getMockTeststartdetails(@QueryMap Map<String, String> params, Callback<PracticeTestStartWithDescription> jsonObjectCallback);

    @GET("/practice_tests/start_test")
    public void getPracticeTest(@QueryMap Map<String, String> params, Callback<PracticeTestStartResponse> jsonObjectCallback);

    @POST("/practice_tests/submit_practice_test")
    public void submitPracticeTest(@QueryMap Map<String, String> params, Callback<PracticeTestResult> jsonObjectCallback);

    @POST("/practice_tests/submit_practice_test")
    public void submitPracticeTestForWriting(@QueryMap Map<String, String> params, Callback<PracticeTestResult> jsonObjectCallback);

    @GET("/assignments/start_test")
    public void getAssignmentTest(@QueryMap Map<String, String> params, Callback<PracticeTestStartResponse> jsonObjectCallback);

    @GET("/mock_tests/start_test")
    public void getMockStartTest(@QueryMap Map<String, String> params, Callback<MockTestStartResponse> jsonObjectCallback);



    @POST("/assignments/submit_assignment")
    public void submitAssignmentTest(@QueryMap Map<String, String> params, Callback<PracticeTestResult> jsonObjectCallback);

    @POST("/mock_tests/submit_question_paper")
    public void submitMockTest(@QueryMap Map<String, String> params, Callback<MockTestSubmitResponse> jsonObjectCallback);



    @GET("/assignments/result")
    public void gettestScore(@QueryMap Map<String, String> params, Callback<PracticeTestResult> jsonObjectCallback);

    @GET("/practice_tests/answer_key")
    public void gettestAnswerScore(@QueryMap Map<String, String> params, Callback<AnswerKeyResponse> jsonObjectCallback);


    @Headers("Authorization: your_token_key_here")
    @Multipart
    @retrofit2.http.POST("practice_tests/speaking_test")
    Call<CommonResponse> uploadAudio(@HeaderMap Map<String, String> token,
                                     @Part MultipartBody.Part file,
                                     @Part MultipartBody.Part file2,
                                     @Part MultipartBody.Part fil3);

    @Headers("Authorization: your_token_key_here")
    @Multipart
    @retrofit2.http.POST("mock_tests/speaking_test")
    Call<CommonResponse> uploadAudioForMoctTest(@HeaderMap Map<String, String> token,
                                     @Part MultipartBody.Part file,
                                     @Part MultipartBody.Part file2,
                                     @Part MultipartBody.Part fil3);


    @POST("/practice_tests/speaking_test")
    public void submitSpeakingPracticeTest(@QueryMap Map<String, String> params, Callback<PracticeTestResult> jsonObjectCallback);

    @GET("/summaries/assignment_scores")
    public void getBarGraphDetails(@QueryMap Map<String, String> params, Callback<GraphResponse> graphResponseCallback);

    @GET("/summaries/mock_test_progress")
    public void getBarGraphForMockTestDetails(@QueryMap Map<String, String> params, Callback<GraphMockTestSectionResponse> graphResponseCallback);

    @GET("/mock_tests/mock_test_categories")
    public void getmockTopicWiseResponse(Callback<MockTestListResponse> jsonObjectCallback);

    @GET("/courses/my_courses")
    public void getCourse(Callback<CourseResponse> jsonObjectCallback);

    @GET("/courses/get_batches")
    public void getBatch(@QueryMap Map<String, String> params, Callback<NewBatchResponse> jsonObjectCallback);

    @GET("/summaries/assignments_completion_progress")
    public void getCourseSummariesResponse(@QueryMap Map<String, String> params, Callback<CourseSummariesResponse> jsonObjectCallback);

    @GET("/mock_tests/category_mock_tests")
    public void getMockTestCountDetails(@QueryMap Map<String, String> params, Callback<MockTestCountResponse> jsonObjectCallback);

    @GET("/mock_tests/result")
    public void getMockTestResult(@QueryMap Map<String, String> params, Callback<MockTestResultResponse> jsonObjectCallback);

    @GET("/mock_tests/view_answers")
    public void getMockTestSubResult(@QueryMap Map<String, String> params, Callback<MockTestFullScoreResponse> jsonObjectCallback);

    @GET("/assignments/view_answers")
    public void getMockTestSubResultForAssignment(@QueryMap Map<String, String> params, Callback<MockTestFullScoreResponse> jsonObjectCallback);

    @GET("/join_ongoing_session")
    public void getongoingLiveSessionDetails( Callback<OngoingLiveSessionResponse> jsonObjectCallback);

    @GET("/study_materials/get_phases")
    public void getstudyMaterialsPhaseResponse( @QueryMap Map<String, String> params,Callback<StudyMaterialsPhaseResponse> jsonObjectCallback);

}
