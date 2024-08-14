package com.devspace.myapplication.detail

import androidx.lifecycle.ViewModel
import app.cash.turbine.test
import com.devspace.myapplication.common.data.remote.model.RecipeDto
import com.devspace.myapplication.detail.presentation.RecipeDetailViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class RecipeDetailViewModelTest : ViewModel() {

    private val fakeService = FakeDetailService()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())

    private val underTest by lazy {
        RecipeDetailViewModel(fakeService, testDispatcher)
    }


    @Test
    fun `fetchDetail returns expected recipe detail`() {
        runTest {
            // Given
            val recipe = RecipeDto(
                title = "Recipe 1",
                id = 1,
                image = "image recipe 1",
                summary = "Summary recipe 1"
            )
            underTest.fetchDetail(recipe.id.toString())

            // When
            underTest.uiRecipeDetail.test {
                // Then assert expected value
                val expected = RecipeDto(
                    title = "Recipe 1",
                    id = 1,
                    image = "image recipe 1",
                    summary = "Summary recipe 1"
                )

                assertEquals(expected.id, awaitItem()?.id)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `fetchDetail logs error when request fails`() {
        runTest {
            // Given
            fakeService.shouldReturnError = true

            // When
            underTest.fetchDetail("invalid_id")
            advanceUntilIdle()

            // Then
            val recipeDetail = underTest.uiRecipeDetail.value
            assertEquals(null, recipeDetail)
        }
    }
}