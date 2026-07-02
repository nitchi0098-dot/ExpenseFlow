package com.expenseflow.app.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.expenseflow.app.ExpenseFlowApp
import com.expenseflow.app.databinding.FragmentDashboardBinding
import com.expenseflow.app.ui.viewmodel.DashboardViewModel
import com.expenseflow.app.ui.viewmodel.DashboardViewModelFactory
import com.expenseflow.app.ui.viewmodel.ExpenseViewModel
import com.expenseflow.app.ui.viewmodel.ExpenseViewModelFactory
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val dashboardViewModel: DashboardViewModel by viewModels {
        DashboardViewModelFactory(ExpenseFlowApp.repository)
    }
    private val expenseViewModel: ExpenseViewModel by viewModels {
        ExpenseViewModelFactory(ExpenseFlowApp.repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            dashboardViewModel.monthlyIncome.collect { income ->
                binding.tvMonthlyIncome.text = "₹$income"
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            dashboardViewModel.pendingExpenses.collect { pending ->
                binding.tvPendingExpenses.text = "₹$pending"
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            dashboardViewModel.paidExpenses.collect { paid ->
                binding.tvPaidExpenses.text = "₹$paid"
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            dashboardViewModel.remainingBalance.collect { balance ->
                binding.tvRemainingBalance.text = "₹$balance"
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            expenseViewModel.expenses.collect { expenses ->
                setupPieChart(expenses)
            }
        }
    }

    private fun setupPieChart(expenses: List<com.expenseflow.app.data.entity.ExpenseEntity>) {
        val categoryTotals = mutableMapOf<String, Float>()
        expenses.forEach { expense ->
            categoryTotals[expense.category] = (categoryTotals[expense.category] ?: 0f) + expense.amount.toFloat()
        }

        val entries = categoryTotals.map { (category, amount) ->
            PieEntry(amount, category)
        }

        val dataSet = PieDataSet(entries, "Expenses by Category").apply {
            colors = ColorTemplate.JOYFUL_COLORS.toList()
            setDrawValues(true)
            valueTextSize = 12f
        }

        val pieData = PieData(dataSet)
        binding.pieChart.apply {
            data = pieData
            description.isEnabled = false
            legend.isEnabled = true
            animateY(1000)
            invalidate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
