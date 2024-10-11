import com.example.repofinder.model.Repository


sealed class Screen {
    object Home : Screen()
    data class Detail(val repository: Repository) : Screen()
}
