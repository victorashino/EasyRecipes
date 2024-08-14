package com.devspace.myapplication.list

import com.devspace.myapplication.common.data.model.Recipe
import com.devspace.myapplication.list.data.RecipeListRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.net.UnknownHostException

class MovieListRepositoryTest {

    private val local = FakeRecipeListLocalDataSource()
    private val remote = FakeRecipeListRemoteDataSource()

    private val underTest by lazy {
        RecipeListRepository(
            local,
            remote
        )
    }

    @Test
    fun `Given no internet connection When getting recipes Then return local data`() {
        runTest {

            // Given
            val localList = listOf(
                Recipe(
                    summary = "Recipe 01 Summary",
                    id = 1,
                    image = "Image recipe 01",
                    title = "Recipe 01"
                )
            )

            remote.recipes = emptyList()
            local.recipes = localList

            //  When
            val result = underTest.getRecipes()

            // Then
            val expected = Result.success(local.getRecipes())
            assertEquals(expected, result)
        }
    }

    @Test
    fun `Given no internet connection and no local data When getting recipes Then return remote result`() {
        runTest {
            // Given
            val remoteException = UnknownHostException("No internet")

            remote.shouldReturnError = true
            remote.exceptionToThrow = remoteException

            remote.recipes = emptyList()
            local.recipes = emptyList()

            // When
            val result = underTest.getRecipes()

            // Then
            assert(result.isFailure)
            result.exceptionOrNull()?.let { exception ->
                assertEquals(remoteException::class, exception::class)
                assertEquals(remoteException.message, exception.message)
            }
        }
    }

    @Test
    fun `Given no internet connection and no local data When getting recipes Then return local result`() {
        runTest {
            // Given
            val localList = listOf(
                Recipe(
                    summary = "Recipe 01 Summary",
                    id = 1,
                    image = "Image recipe 01",
                    title = "Recipe 01"
                )
            )

            val remoteException = UnknownHostException("No internet")

            remote.shouldReturnError = true
            remote.exceptionToThrow = remoteException

            remote.recipes = emptyList()
            local.recipes = localList

            // When
            val result = underTest.getRecipes()

            // Then
            val expected = Result.success(localList)
            assert(result.isSuccess)
            assertEquals(expected, result)
        }
    }

    @Test
    fun `Given remote success When getting now playing movies Then update local data`() {
        runTest {
            // Given
            val list = listOf(
                Recipe(
                    summary = "Recipe 01 Summary",
                    id = 1,
                    image = "Image recipe 01",
                    title = "Recipe 01"
                )
            )
            val remoteResult = Result.success(list)
            remote.recipes = remoteResult.getOrNull()!!
            local.recipes = list

            // When
            val result = underTest.getRecipes()

            // Then
            val expected = Result.success(list)
            assertEquals(expected, result)
            assertEquals(local.updateItems, list)
        }
    }

    @Test
    fun `Given remote throws exception When getting now playing movies Then return failure result`() {
        runTest {
            // Given
            val exception = Exception("Something went wrong")
            remote.shouldReturnError = true
            remote.exceptionToThrow = exception

            // When
            val result = underTest.getRecipes()

            // Then
            assert(result.isFailure)
            result.exceptionOrNull()?.let { ex ->
                assertEquals(exception::class, ex::class)
                assertEquals(exception.message, ex.message)
            }
        }
    }
}