# ExpenseFlow - Development Guide

## Architecture

This project follows MVVM (Model-View-ViewModel) architecture pattern:

### Model Layer
- **Entity**: Database entity classes (ExpenseEntity, CategoryEntity, IncomeEntity)
- **Dao**: Data Access Objects for database operations
- **Database**: Room database configuration
- **Repository**: Single source of truth for data

### ViewModel Layer
- Manages UI-related data
- Handles business logic
- Observes and exposes data to UI

### View Layer
- Fragments for UI screens
- Activities for host containers
- Adapters for RecyclerView
- Layout XMLs for UI design

## Database Schema

### Expenses Table
```sql
CREATE TABLE expenses (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title TEXT NOT NULL,
    amount REAL NOT NULL,
    category TEXT NOT NULL,
    description TEXT,
    date INTEGER,
    isPaid INTEGER DEFAULT 0,
    createdAt INTEGER,
    updatedAt INTEGER
)
```

### Categories Table
```sql
CREATE TABLE categories (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    icon TEXT,
    color TEXT,
    createdAt INTEGER
)
```

### Income Table
```sql
CREATE TABLE income (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    amount REAL NOT NULL,
    month INTEGER,
    year INTEGER,
    createdAt INTEGER,
    updatedAt INTEGER
)
```

## Key Classes

### Entities
- `ExpenseEntity`: Represents a single expense
- `CategoryEntity`: Represents an expense category
- `IncomeEntity`: Represents monthly income

### DAOs
- `ExpenseDao`: CRUD operations for expenses
- `CategoryDao`: CRUD operations for categories
- `IncomeDao`: CRUD operations for income

### ViewModels
- `ExpenseViewModel`: Manages expense data and operations
- `CategoryViewModel`: Manages category data and operations
- `DashboardViewModel`: Manages dashboard statistics

### Fragments
- `DashboardFragment`: Main dashboard with statistics
- `ExpensesFragment`: List view of all expenses
- `AddExpenseFragment`: Form to add new expense
- `CategoriesFragment`: Grid view of categories

## State Management

Using Kotlin Flow and StateFlow for reactive data binding:

```kotlin
// ViewModel
private val _expenses = MutableStateFlow<List<ExpenseEntity>>(emptyList())
val expenses: StateFlow<List<ExpenseEntity>> = _expenses.asStateFlow()

// Fragment
viewLifecycleOwner.lifecycleScope.launch {
    viewModel.expenses.collect { expenses ->
        adapter.submitList(expenses)
    }
}
```

## UI/UX Guidelines

### Material 3 Design
- Use Material 3 components (MaterialButton, MaterialCardView, etc.)
- Follow color system (Primary, Secondary, Tertiary)
- Maintain consistent spacing and padding

### Glassmorphism
- Semi-transparent backgrounds with blur effect
- Subtle shadows and borders
- Modern, clean aesthetic

### Dark Mode
- Automatically supported via Material 3
- Different color palettes for light and dark themes
- Colors defined in values/colors.xml and values-night/colors.xml

## Code Style

### Naming Conventions
- Classes: PascalCase (ExpenseViewModel)
- Functions: camelCase (loadExpenses)
- Constants: UPPER_CASE (DEFAULT_TIMEOUT)
- Private fields: prefix with underscore (_binding)

### Kotlin Best Practices
- Use extension functions for common operations
- Prefer val over var
- Use data classes for models
- Use sealed classes for type-safe callbacks

## Testing

### Unit Tests
- Test ViewModel logic
- Mock Repository
- Verify StateFlow emissions

### Instrumented Tests
- Test DAO operations
- Test database migrations
- Test UI interactions

## Dependencies

See `build.gradle.kts` for complete list:
- AndroidX Core and AppCompat
- Material 3
- Room Database
- Navigation
- Coroutines
- MPAndroidChart

## Future Enhancements

- [ ] Cloud synchronization
- [ ] Expense import/export
- [ ] Budget alerts
- [ ] Monthly reports
- [ ] Multi-currency support
- [ ] Recurring expenses
- [ ] Receipt OCR
- [ ] Expense sharing

## Troubleshooting

### Database Issues
- Clear app data if schema changes
- Check Room migration scripts
- Verify entity annotations

### UI Issues
- Ensure ViewBinding is enabled in build.gradle
- Check layout file names match fragment references
- Verify navigation graph configuration

### Compilation Issues
- Run `./gradlew clean build`
- Invalidate Android Studio cache
- Update Gradle and dependencies
