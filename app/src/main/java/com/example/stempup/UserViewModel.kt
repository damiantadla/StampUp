import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser

class UserViewModel : ViewModel() {
    var currentUser: FirebaseUser? = null
        private set
    var number = 0
    fun setUser(user: FirebaseUser?) {
        currentUser = user
    }
}