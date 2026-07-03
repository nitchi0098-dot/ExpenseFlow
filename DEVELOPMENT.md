# ExpenseFlow Development Guide

## Architecture Overview

ExpenseFlow follows the MVVM (Model-View-ViewModel) architecture pattern:

```
UI Layer (Fragments/Activities)
         ↓
    ViewModel (StateFlow)
         ↓
    Repository
         ↓
    DAO (Data Access Objects)
         ↓
    Room Database
```

## Data Flow

1. **UI Layer**: Fragments and Activities display data
2. **ViewModel**: Manages UI state using StateFlow and coroutines
3. **Repository**: Provides data abstraction layer
4. **DAO**: Room database access layer
5. **Database**: SQLite database via Room ORM

## Adding New Features

### 1. Create Entity (if needed)
```kotlin
@Entity(tableName = "table_name")
data class NewEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    // fields
)
```

### 2. Create DAO
```kotlin
@Dao
interface NewDao {
    @Insert
    suspend fun insert(entity: NewEntity)
    
    @Query("SELECT * FROM table_name")
    fun getAll(): Flow<List<NewEntity>>
}
```

### 3. Update Database
```kotlin
@Database(
    entities = [ExistingEntity::class, NewEntity::class],
    version = 2 // Increment version
)
abstract class ExpenseFlowDatabase : RoomDatabase() {
    abstract fun newDao(): NewDao
}
```

### 4. Create ViewModel
```kotlin
class NewViewModel(private val repository: ExpenseRepository) : ViewModel() {
    private val _data = MutableStateFlow<List<NewEntity>>(emptyList())
    val data: StateFlow<List<NewEntity>> = _data.asStateFlow()
    
    init {
        loadData()
    }
    
    private fun loadData() {
        viewModelScope.launch {
            repository.getNewData().collect { items ->
                _data.value = items
            }
        }
    }
}
```

### 5. Create Fragment/UI
- Create XML layout in `res/layout/`
- Create Fragment class with ViewBinding
- Observe ViewModel data

## Code Style Guidelines

- Use Kotlin naming conventions (camelCase for variables/functions, PascalCase for classes)
- Keep functions under 20 lines when possible
- Use extension functions for common operations
- Add type hints for better code readability
- Document public APIs with KDoc

## Testing

### Unit Tests
```bash
./gradlew test
```

### Instrumented Tests
```bash
./gradlew connectedAndroidTest
```

## Performance Tips

1. Use `StateFlow` instead of `LiveData` for better performance
2. Batch database operations with Room transactions
3. Use `Flow` for reactive data streams
4. Implement proper pagination for large datasets
5. Use `debounce` for search queries

## Debugging

- Use Android Studio's Logcat for logs
- Set breakpoints in the debugger
- Use Database Inspector for Room database inspection
- Use Layout Inspector for UI debugging

## Resources

- [Android Architecture Guide](https://developer.android.com/topic/architecture)
- [Room Database Documentation](https://developer.android.com/training/data-storage/room)
- [Material 3 Design System](https://m3.material.io/)
- [Jetpack Navigation](https://developer.android.com/guide/navigation)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
