package com.devspace.myapplication.list

import app.cash.turbine.test
import com.devspace.myapplication.common.data.model.Recipe
import com.devspace.myapplication.list.presentation.RecipeListViewModel
import com.devspace.myapplication.list.presentation.ui.RecipeListUiState
import com.devspace.myapplication.list.presentation.ui.RecipeUiData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.net.UnknownHostException

class MovieListViewModelTest {

    private val local = FakeRecipeListLocalDataSource()
    private val remote = FakeRecipeListRemoteDataSource()
    private val repository = FakeListRepository(local, remote)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())

    private val underTest by lazy {
        RecipeListViewModel(repository, testDispatcher)
    }

    @Test
    fun `Given fresh viewModel When collecting to recipes Then assert expected value`() {
        runTest {
            // Given
            val recipes = listOf(
                Recipe(
                    summary = "Recipe 01 Summary",
                    id = 1,
                    image = "Image recipe 01",
                    title = "Recipe 01"
                )
            )
            repository.recipesResult = Result.success(recipes)

            // When
            underTest.uiRandomRecipes.test {
                // Then assert expected value
                val expected = RecipeListUiState(
                    list = listOf(
                        RecipeUiData(
                            summary = "Recipe 01 Summary",
                            id = 1,
                            image = "Image recipe 01",
                            title = "Recipe 01"
                        )
                    )
                )

                assertEquals(expected, awaitItem())
            }
        }
    }

    @Test
    fun `Given fresh viewModel When recipes result is failure Then Exception is UnknownHostException`() = runTest {
        // Given
        repository.recipesResult = Result.failure(UnknownHostException())

        // When
        underTest.uiRandomRecipes.test {
            // Then
            val expected = RecipeListUiState(
                isLoading = false,
                isError = true,
                messageError = "Not Internet connection"
            )
            assertEquals(expected, awaitItem())
        }
    }

    @Test
    fun `Given ViewModel When recipes fails with non-UnknownHostException Then UI shows error`() = runTest {
        // Given
        repository.recipesResult = Result.failure(Exception())

        // When
        underTest.uiRandomRecipes.test {
            // Then
            val expected = RecipeListUiState(isError = true)
            assertEquals(expected, awaitItem())
        }
    }

}