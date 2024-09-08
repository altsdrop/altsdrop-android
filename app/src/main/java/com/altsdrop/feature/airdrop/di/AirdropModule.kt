package com.altsdrop.feature.airdrop.di

import com.altsdrop.core.util.FirestoreConstants
import com.altsdrop.feature.airdrop.data.repository.AirdropRepositoryImpl
import com.altsdrop.feature.airdrop.domain.repository.AirdropRepository
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
object AirdropModule {

    @Provides
    @Named(FirestoreConstants.AIRDROP_COLLECTION)
    fun provideAirdropCollectionRef(firestore: FirebaseFirestore): CollectionReference {
        return firestore.collection(FirestoreConstants.AIRDROP_COLLECTION)
    }

    @Provides
    fun provideAirdropRepository(
        @Named(FirestoreConstants.AIRDROP_COLLECTION)
        airdropCollectionRef: CollectionReference
    ): AirdropRepository {
        return AirdropRepositoryImpl(airdropCollectionRef)
    }
}