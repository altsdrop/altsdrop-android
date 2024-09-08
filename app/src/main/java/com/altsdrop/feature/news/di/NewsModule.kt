package com.altsdrop.feature.news.di

import com.altsdrop.core.util.FirestoreConstants
import com.altsdrop.feature.news.data.repository.NewsRepositoryImpl
import com.altsdrop.feature.news.domain.repository.NewsRepository
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
object NewsModule {

    @Provides
    @Named(FirestoreConstants.NEWS_COLLECTION)
    fun provideNewsCollectionRef(firestore: FirebaseFirestore): CollectionReference {
        return firestore.collection(FirestoreConstants.NEWS_COLLECTION)
    }

    @Provides
    fun provideNewsRepository(
        @Named(FirestoreConstants.NEWS_COLLECTION)
        newsCollectionRef: CollectionReference
    ): NewsRepository {
        return NewsRepositoryImpl(newsCollectionRef)
    }
}