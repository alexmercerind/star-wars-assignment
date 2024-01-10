# Star Wars

Assignment for Groww : Android Developer : SDE I

## Snapshots

<table>
  <tr>
    <td><img src="https://github.com/alexmercerind/star-wars-assignment/assets/28951144/1d067dd7-2699-4d16-9429-50d82cebc748"></td>
    <td><img src="https://github.com/alexmercerind/star-wars-assignment/assets/28951144/6f27c3cf-5a18-4d3d-aa36-8b85f6e8554a"></td>
    <td><img src="https://github.com/alexmercerind/star-wars-assignment/assets/28951144/80d12a92-8a08-4709-8e99-d561d7b1bfb8"></td>
  </tr>
</table>

## Recording

https://github.com/alexmercerind/star-wars-assignment/assets/28951144/096b83f8-dff9-4a1f-a28b-971bc3e080ec

## Dependencies

- Paging3
- Retrofit
- Room
- Gson
- OkHttp
- Navigation Component ( + safe-args)
- Dagger Hilt

## Features

- MVVM
- Display all characters as grid w/ Paging3 + Retrofit
- Display all movies from a character as grid w/ Paging3 + Retrofit
- Filter using name & gender w/ BottomSheetDialogFragment
- Offline support w/ Room
- Single activity & multiple fragment architecture w/ Navigation Component & safe-args
- Dependency injection using Dagger Hilt
- Material Design 3

## Architecture

- api
  - dto
    - CharacterDTO
    - CharacterPageDTO
    - FilmDTO
  - StarWarsAPI
- db
  - converters
    - ListConverter
  - dao
    - CharacterDao
    - FilmDao
  - StarWarsDatabase
- di: Dagger Hilt dependency-injection
  - ApplicationModule
- mappers: Convert b/w DTO & models
- model
  - Character
  - Film
- paging: PagingSource<Key, Model>
  - CharactersPageSource
  - FilmsPageSource
- repository
  - StarWarsRepository
- ui
  - adapter
    - CharacterAdapter
    - FilmAdapter
    - GenericLoadStateAdapter
  - CharactersListFragment
  - FilmsListFragment
  - CharactersListViewModel
  - FilmsListViewModel
  - FilterBottomSheet
  - MainActivity
  - MainActivityViewModel

## Author

[Hitesh Kumar Saini](https://github.com/alexmercerind)
