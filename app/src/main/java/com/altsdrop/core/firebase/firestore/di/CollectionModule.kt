package com.altsdrop.core.firebase.firestore.di

import com.altsdrop.core.firebase.firestore.util.FirestoreConstants
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
object CollectionModule {
    @Provides
    @Named(FirestoreConstants.COLLECTION_META_DATA)
    fun provideCollectionMetaDataRef(firestore: FirebaseFirestore): CollectionReference {
        return firestore.collection(FirestoreConstants.COLLECTION_META_DATA)
    }
}