# Java File Organizer

This is a Java command-line application for organizing files in a directory based on their file extensions. The application scans a specified directory and moves files into subdirectories based on their extensions.

## Features

- Organizes files in a directory into subdirectories based on their file extensions
- Creates subdirectories based on the file extensions if they do not already exist
- Supports manual configurations of where each type of file is supposed to go

## Usage

To use the Java File Organizer, clone the repository.
Then select the directories you want to be organized in "Directories.txt".\

Add configs to "Folders.txt" if you have files that are not listed there. I used my most used files to create the default configuration. Note that any files, you do not state explicitly will be moved to Misc folder.\

Finally, just run the "run.bat" file. This will open the application in your system tray, organize initially the selected directories and keep organizing any files you add to them.

If you want to close the application, just right-click on the icon and select "Close".

I recommend using the app as a start-up application, which would organize your files while you are using your device. In order to do so, add a shortcut to "run.bat" into shell:startup folder.
## Contributing

Contributions are welcome! If you find a bug or have an idea for a new feature, please open an issue or submit a pull request.

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.
