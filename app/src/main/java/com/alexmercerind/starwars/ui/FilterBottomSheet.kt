import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alexmercerind.starwars.databinding.LayoutFilterBottomSheetBinding
import com.alexmercerind.starwars.ui.CharactersListViewModel
import com.alexmercerind.starwars.ui.adapter.CharacterAdapter
import com.alexmercerind.starwars.utils.Constants
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FilterBottomSheet(
    private val charactersListViewModel: CharactersListViewModel,
    private val pagingAdapter: CharacterAdapter
) : BottomSheetDialogFragment() {
    private var _binding: LayoutFilterBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = LayoutFilterBottomSheetBinding.inflate(inflater, container, false)

        binding.nameTextInputEditText.setText(charactersListViewModel.pagingSource.name)

        binding.maleChip.isChecked = charactersListViewModel.pagingSource.genders.contains(Constants.GENDER_MALE)
        binding.femaleChip.isChecked = charactersListViewModel.pagingSource.genders.contains(Constants.GENDER_FEMALE)
        binding.othersChip.isChecked = charactersListViewModel.pagingSource.genders.contains(Constants.GENDER_OTHERS)

        binding.applyMaterialButton.setOnClickListener {
            charactersListViewModel.apply {
                resetPagingSource()
                pagingSource.name = binding.nameTextInputEditText.text.toString()
                if (binding.maleChip.isChecked) {
                    pagingSource.addGender(Constants.GENDER_MALE)
                } else {
                    pagingSource.removeGender(Constants.GENDER_MALE)
                }
                if (binding.femaleChip.isChecked) {
                    pagingSource.addGender(Constants.GENDER_FEMALE)
                } else {
                    pagingSource.removeGender(Constants.GENDER_FEMALE)
                }
                if (binding.othersChip.isChecked) {
                    pagingSource.addGender(Constants.GENDER_OTHERS)
                } else {
                    pagingSource.removeGender(Constants.GENDER_OTHERS)
                }
                pagingAdapter.refresh()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "FilterBottomSheet"
    }
}
