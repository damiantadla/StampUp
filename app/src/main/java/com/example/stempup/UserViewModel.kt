import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser

class UserViewModel : ViewModel() {

    var currentUser: FirebaseUser? = null
        private set

    fun setUser(user: FirebaseUser?) {
        currentUser = user
    }

    fun logout() {
        currentUser = null
    }
}