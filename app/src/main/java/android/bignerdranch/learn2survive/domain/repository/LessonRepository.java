package android.bignerdranch.learn2survive.domain.repository;

import android.bignerdranch.learn2survive.domain.model.DisasterType;
import android.bignerdranch.learn2survive.domain.model.Lesson;
import android.bignerdranch.learn2survive.domain.model.UserProgress;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public interface LessonRepository {
    Task<QuerySnapshot> getAllLessons();
    Task<DocumentSnapshot> getLessonById(String lessonId);
    Task<QuerySnapshot> getLessonsByDisasterType(DisasterType disasterType);
    Task<Void> saveUserProgress(UserProgress progress);
    Task<DocumentSnapshot> getUserProgress(String userId, String lessonId);
    Task<QuerySnapshot> getAllUserProgress(String userId);
    Task<Void> bookmarkLesson(String userId, String lessonId, boolean bookmarked);
    Task<Void> markSectionCompleted(String userId, String lessonId, String sectionId);
    Task<Void> markLessonCompleted(String userId, String lessonId);
}
