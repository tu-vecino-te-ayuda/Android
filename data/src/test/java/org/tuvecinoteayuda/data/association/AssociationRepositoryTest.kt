package org.tuvecinoteayuda.data.association

import com.google.gson.Gson
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.tuvecinoteayuda.data.ResultWrapper
import org.tuvecinoteayuda.data.association.models.AssociationListResponse
import org.tuvecinoteayuda.data.commons.models.MessageResponse
import org.tuvecinoteayuda.data.association.repository.AssociationsRepository
import org.tuvecinoteayuda.data.commons.ALL_ASSOCIATIONS_RESPONSE_OK
import org.tuvecinoteayuda.data.commons.DETACH_ASSOCIATIONS_RESPONSE_OK
import org.tuvecinoteayuda.data.commons.JOIN_ASSOCIATION_RESPONSE_OK
import org.tuvecinoteayuda.data.commons.MY_ASSOCIATIONS_RESPONSE_OK

class AssociationRepositoryTest {

    private val repository = mockk<AssociationsRepository>(relaxed = true)

    @Test
    fun `get all association is success`() {
        runBlockingTest {
            val associationList =
                Gson().fromJson<AssociationListResponse>(ALL_ASSOCIATIONS_RESPONSE_OK, AssociationListResponse::class.java)

            coEvery {
                repository.getAllAssociations()
            } returns ResultWrapper.Success(associationList)

            val result =   repository.getAllAssociations()
            assertEquals(ResultWrapper.Success(associationList), result)
        }
    }

    @Test
    fun `get my association is success`() {
        runBlockingTest {
            val associationList =
                Gson().fromJson<AssociationListResponse>(MY_ASSOCIATIONS_RESPONSE_OK, AssociationListResponse::class.java)

            coEvery {
                repository.getAllAssociations()
            } returns ResultWrapper.Success(associationList)

            val result =   repository.getAllAssociations()
            assertEquals(ResultWrapper.Success(associationList), result)
        }
    }

    @Test
    fun `join association is success`() {
        runBlockingTest {
            val message =
                Gson().fromJson<MessageResponse>(JOIN_ASSOCIATION_RESPONSE_OK, MessageResponse::class.java)

            coEvery {
                repository.joinAssocitation(any())
            } returns ResultWrapper.Success(message)

            val result =   repository.joinAssocitation("")
            assertEquals(ResultWrapper.Success(message), result)
        }
    }

    @Test
    fun `detach association is success`() {
        runBlockingTest {
            val message =
                Gson().fromJson<MessageResponse>(DETACH_ASSOCIATIONS_RESPONSE_OK, MessageResponse::class.java)

            coEvery {
                repository.detachAssocitation(any())
            } returns ResultWrapper.Success(message)

            val result =   repository.detachAssocitation("")
            assertEquals(ResultWrapper.Success(message), result)
        }
    }

}