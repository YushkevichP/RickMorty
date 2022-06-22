//package com.example.hm7_cleanarchitecture.domain.usecase
//
//import com.example.hm7_cleanarchitecture.domain.model.LceState
//import com.example.hm7_cleanarchitecture.domain.model.Person
//import com.example.hm7_cleanarchitecture.domain.repository.FavouritesRepository
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.flow
//
//class GetFavouriteUseCase(
//    private val favouritesRepository: FavouritesRepository
//) {
//    operator fun invoke(): Flow<LceState<List<Person>>> = flow {
//
//        val cache = favouritesRepository.getFavourites(AMOUNT_LIMIT)
//        emit(LceState.Content(cache))
//    }
//
//    companion object {
//        private const val AMOUNT_LIMIT = 20
//    }
//}