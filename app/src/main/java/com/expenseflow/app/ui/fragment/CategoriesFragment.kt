package com.expenseflow.app.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.expenseflow.app.ExpenseFlowApp
import com.expenseflow.app.data.entity.CategoryEntity
import com.expenseflow.app.databinding.FragmentCategoriesBinding
import com.expenseflow.app.ui.adapter.CategoryAdapter
import com.expenseflow.app.ui.viewmodel.CategoryViewModel
import com.expenseflow.app.ui.viewmodel.CategoryViewModelFactory
import kotlinx.coroutines.launch

class CategoriesFragment : Fragment() {
    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CategoryViewModel by viewModels {
        CategoryViewModelFactory(ExpenseFlowApp.repository)
    }
    private val adapter by lazy {
        CategoryAdapter { category ->
            // Handle category click
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeCategories()
        setupAddButton()
    }

    private fun setupRecyclerView() {
        binding.rvCategories.adapter = adapter
    }

    private fun observeCategories() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.categories.collect { categories ->
                adapter.submitList(categories)
            }
        }
    }

    private fun setupAddButton() {
        binding.fabAddCategory.setOnClickListener {
            val newCategory = CategoryEntity(
                name = "New Category",
                icon = "💰"
            )
            viewModel.addCategory(newCategory)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
