# Movie Mania

<img src="/screenshots/icons.jpg" height="200px"/> <br>

##### How to build the project?
Build the project in studio or write following commands
Open terminal and type the below command to generate debug build below are commands for Linux and Mac OS users
```
./gradlew assembleDebug
```

To build and install the app in running emulator
```
./gradlew installDebug
```

##### Or Just download the apk file in from [here](/builds/app-debug.apk)

##### How its being developed?:

All the android new Best Practises have been implemented:

- Android **navigational Component** for navigation between fragment and achieving a single activity based application
- **MVVM** architecture with deferring all complexities of UI to viewModel and repository.
- Using Manual for dependency Injection
- Simplicity and easy design of the app (Inspired by Indicative UI provided )

##### Some Screenshots:

<div style="display:flex | space-evenly;" >
    <img src="/screenshots/1.png" height="400px" />
    <img src="/screenshots/2.png" height="400px" />
</div>
<div>
<br>
<div style="display:flex | space-evenly;" >
    <img src="/screenshots/3.png" height="400px" />
    <img src="/screenshots/4.png" height="400px" />
</div>
<br>

##### Features
**The application has following features implemented**

- Users can browse the movies
- Sort the movies as per year and name
- Filter the movies on basis of name, genre and ratings
- Clean Architecture with simple UI

## Architecture followed MVVM

## Libraries Used
- **Glide** for Image
- hdodenhof:circleimageview for Circular Images
- balysv:material-ripple for ripple effect

##### Decisions

1. Decision to delay the time while reading json file

5. Decision to use MVVM archiecture

### TODOs
- [x] Sorting and filtering
- [x] Searching
- [ ] Pagination
- [x] Navigation component
- [x] ripple effect
- [x] Awesome cards
- [x] MVVM architecture

