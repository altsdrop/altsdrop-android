package com.altsdrop.feature.dapp.di

import com.altsdrop.core.database.dao.DappDao
import com.altsdrop.core.firebase.firestore.util.FirestoreConstants
import com.altsdrop.feature.dapp.data.repository.DappRepositoryImpl
import com.altsdrop.feature.dapp.domain.repository.DappRepository
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
object DappModule {
    @Provides
    @Named(FirestoreConstants.DAPP_COLLECTION)
    fun provideDappCollectionRef(firestore: FirebaseFirestore): CollectionReference {
        return firestore.collection(FirestoreConstants.DAPP_COLLECTION)
    }

    @Provides
    fun provideDappRepository(
        @Named(FirestoreConstants.DAPP_COLLECTION)
        dappCollectionRef: CollectionReference,
        dappDao: DappDao
    ): DappRepository {
        return DappRepositoryImpl(dappCollectionRef, dappDao)
    }
}