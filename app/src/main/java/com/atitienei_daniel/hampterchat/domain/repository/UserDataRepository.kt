package com.atitienei_daniel.hampterchat.domain.repository

import com.atitienei_daniel.hampterchat.domain.model.Person
import com.atitienei_daniel.hampterchat.domain.model.User
import com.atitienei_daniel.hampterchat.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {

    fun getUserData(): Flow<Resource<User>>
}