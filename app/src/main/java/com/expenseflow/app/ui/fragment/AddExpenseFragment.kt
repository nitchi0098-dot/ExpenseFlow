package com.expenseflow.app.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.expenseflow.app.ExpenseFlowApp
import com.expenseflow.app.data.entity.ExpenseEntity
import com.expenseflow.app.databinding.FragmentAddExpenseBinding
import com.expenseflow.app.ui.viewmodel.CategoryViewModel
import com.expenseflow.app.ui.viewmodel.CategoryViewModelFactory
import com.expenseflow.app.ui.viewmodel.ExpenseViewModel
import com.expenseflow.app.ui.viewmodel.ExpenseViewModelFactory
import kotlinx.coroutines.launch

class AddExpenseFragment : Fragment() {
    private var _binding: FragmentAddExpenseBinding? = null
    private val binding get() = _binding!!
    private val expenseViewModel: ExpenseViewModel by viewModels {
        ExpenseViewModelFactory(ExpenseFlowApp.repository)
    }
    private val categoryViewModel: CategoryViewModel by viewModels {
        CategoryViewModelFactory(ExpenseFlowApp.repository)
    }
    private var selectedCategory: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddExpenseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCategoryDropdown()
        setupSaveButton()
    }

    private fun setupCategoryDropdown() {
        viewLifecycleOwner.lifecycleScope.launch {
            categoryViewModel.categories.collect { categories ->
                val categoryNames = categories.map { it.name }.toTypedArray()
                val adapter = android.widget.ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    categoryNames
                )
                binding.actvCategory.setAdapter(adapter)
                binding.actvCategory.setOnItemClickListener { _, _, position, _ ->
                    selectedCategory = categoryNames[position]
                }
            }
        }
    }

    private fun setupSaveButton() {
        binding.btnSaveExpense.setOnClickListener {
            val title = binding.etExpenseTitle.text.toString()
            val amountStr = binding.etExpenseAmount.text.toString()
            val description = binding.etExpenseDescription.text.toString()

            if (title.isEmpty() || amountStr.isEmpty() || selectedCategory.isEmpty()) {
                return@setOnClickListener
            }

            val amount = amountStr.toDouble()
            val expense = ExpenseEntity(
                title = title,
                amount = amount,
                category = selectedCategory,
                description = description
            )

            expenseViewModel.addExpense(expense)
            requireActivity().onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
