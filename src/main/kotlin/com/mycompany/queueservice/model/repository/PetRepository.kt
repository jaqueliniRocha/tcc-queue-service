package com.mycompany.queueservice.model.repository

import com.mycompany.queueservice.model.Pet
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PetRepository : CrudRepository<Pet, Long> {
}