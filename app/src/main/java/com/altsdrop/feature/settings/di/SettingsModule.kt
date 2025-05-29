package com.altsdrop.feature.settings.di

import com.altsdrop.core.firebase.firestore.util.FirestoreConstants
import com.altsdrop.feature.settings.data.repository.BugReportRepositoryImpl
import com.altsdrop.feature.settings.data.repository.FeedbackRepositoryImpl
import com.altsdrop.feature.settings.data.repository.RemoteSettingsRepositoryImpl
import com.altsdrop.feature.settings.data.repository.UserRepositoryImpl
import com.altsdrop.feature.settings.domain.repository.BugReportRepository
import com.altsdrop.feature.settings.domain.repository.FeedbackRepository
import com.altsdrop.feature.settings.domain.repository.RemoteSettingsRepository
import com.altsdrop.feature.settings.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
object SettingsModule {

    @Provides
    fun provideUserRepository(firebaseAuth: FirebaseAuth): UserRepository {
        return UserRepositoryImpl(firebaseAuth)
    }

    @Provides
    @Named(FirestoreConstants.FEEDBACK_COLLECTION)
    fun provideFeedbackCollectionRef(firestore: FirebaseFirestore): CollectionReference {
        return firestore.collection(FirestoreConstants.FEEDBACK_COLLECTION)
    }

    @Provides
    fun provideFeedbackRepository(
        @Named(FirestoreConstants.FEEDBACK_COLLECTION)
        feedbackCollectionRef: CollectionReference
    ): FeedbackRepository {
        return FeedbackRepositoryImpl(feedbackCollectionRef)
    }

    @Provides
    @Named(FirestoreConstants.BUG_REPORT_COLLECTION)
    fun provideBugReportCollectionRef(firestore: FirebaseFirestore): CollectionReference {
        return firestore.collection(FirestoreConstants.BUG_REPORT_COLLECTION)
    }

    @Provides
    fun provideBugReportRepository(
        @Named(FirestoreConstants.BUG_REPORT_COLLECTION)
        bugReportCollectionRef: CollectionReference
    ): BugReportRepository {
        return BugReportRepositoryImpl(bugReportCollectionRef)
    }

    @Provides
    fun provideRemoteSettingsRepository(
        remoteConfig: FirebaseRemoteConfig,
        moshi: Moshi
    ): RemoteSettingsRepository {
        return RemoteSettingsRepositoryImpl(remoteConfig, moshi)
    }
}