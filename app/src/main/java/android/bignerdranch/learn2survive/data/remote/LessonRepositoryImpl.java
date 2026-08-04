package android.bignerdranch.learn2survive.data.remote;

import android.bignerdranch.learn2survive.domain.model.DisasterType;
import android.bignerdranch.learn2survive.domain.model.Lesson;
import android.bignerdranch.learn2survive.domain.model.UserProgress;
import android.bignerdranch.learn2survive.domain.repository.LessonRepository;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LessonRepositoryImpl implements LessonRepository {
    private static final String COLLECTION_LESSONS = "lessons";
    private static final String COLLECTION_USER_PROGRESS = "user_progress";
    private final FirebaseFirestore db;

    public LessonRepositoryImpl() {
        this.db = FirebaseFirestore.getInstance();
    }

    @Override
    public Task<QuerySnapshot> getAllLessons() {
        return db.collection(COLLECTION_LESSONS)
                .orderBy("disasterType")
                .get();
    }

    @Override
    public Task<DocumentSnapshot> getLessonById(String lessonId) {
        return db.collection(COLLECTION_LESSONS)
                .document(lessonId)
                .get();
    }

    @Override
    public Task<QuerySnapshot> getLessonsByDisasterType(DisasterType disasterType) {
        return db.collection(COLLECTION_LESSONS)
                .whereEqualTo("disasterType", disasterType.getValue())
                .get();
    }

    @Override
    public Task<Void> saveUserProgress(UserProgress progress) {
        DocumentReference docRef = db.collection(COLLECTION_USER_PROGRESS)
                .document(progress.getUserId() + "_" + progress.getLessonId());
        
        Map<String, Object> data = new HashMap<>();
        data.put("userId", progress.getUserId());
        data.put("lessonId", progress.getLessonId());
        data.put("completedSections", progress.getCompletedSections());
        data.put("totalSections", progress.getTotalSections());
        data.put("isBookmarked", progress.isBookmarked());
        data.put("isCompleted", progress.isCompleted());
        data.put("completedSectionIds", progress.getCompletedSectionIds());
        data.put("lastAccessedAt", progress.getLastAccessedAt());
        data.put("completedAt", progress.getCompletedAt());
        
        return docRef.set(data);
    }

    @Override
    public Task<DocumentSnapshot> getUserProgress(String userId, String lessonId) {
        return db.collection(COLLECTION_USER_PROGRESS)
                .document(userId + "_" + lessonId)
                .get();
    }

    @Override
    public Task<QuerySnapshot> getAllUserProgress(String userId) {
        return db.collection(COLLECTION_USER_PROGRESS)
                .whereEqualTo("userId", userId)
                .get();
    }

    @Override
    public Task<Void> bookmarkLesson(String userId, String lessonId, boolean bookmarked) {
        DocumentReference docRef = db.collection(COLLECTION_USER_PROGRESS)
                .document(userId + "_" + lessonId);
        
        return docRef.update("isBookmarked", bookmarked, "lastAccessedAt", new Date());
    }

    @Override
    public Task<Void> markSectionCompleted(String userId, String lessonId, String sectionId) {
        DocumentReference docRef = db.collection(COLLECTION_USER_PROGRESS)
                .document(userId + "_" + lessonId);
        
        return dbRef.update("completedSectionIds", FieldValue.arrayUnion(sectionId),
                          "lastAccessedAt", new Date());
    }

    @Override
    public Task<Void> markLessonCompleted(String userId, String lessonId) {
        DocumentReference docRef = db.collection(COLLECTION_USER_PROGRESS)
                .document(userId + "_" + lessonId);
        
        return docRef.update("isCompleted", true,
                          "completedAt", new Date(),
                          "lastAccessedAt", new Date());
    }
}
