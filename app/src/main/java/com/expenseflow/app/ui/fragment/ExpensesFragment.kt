package com.expenseflow.app.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.expenseflow.app.ExpenseFlowApp
import com.expenseflow.app.databinding.FragmentExpensesBinding
import com.expenseflow.app.ui.adapter.ExpenseAdapter
import com.expenseflow.app.ui.viewmodel.ExpenseViewModel
import com.expenseflow.app.ui.viewmodel.ExpenseViewModelFactory
import kotlinx.coroutines.launch

class ExpensesFragment : Fragment() {
    private var _binding: FragmentExpensesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ExpenseViewModel by viewModels {
        ExpenseViewModelFactory(ExpenseFlowApp.repository)
    }
    private val adapter by lazy {
        ExpenseAdapter(
            onItemClick = { expense ->
                // Handle item click
            },
            onMarkPaid = { expense ->
                viewModel.markAsPaid(expense)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExpensesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeExpenses()
        setupSearch()
    }

    private fun setupRecyclerView() {
        binding.rvExpenses.adapter = adapter
    }

    private fun observeExpenses() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.expenses.collect { expenses ->
                adapter.submitList(expenses)
            }
        }
    }

    private fun setupSearch() {
        binding.searchBar.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    viewModel.searchExpenses(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    viewModel.expenses
                }
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
