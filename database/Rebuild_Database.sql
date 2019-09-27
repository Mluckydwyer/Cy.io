# Create the Database
Create Database Cyio;
Use Cyio;

# Nunk / Delete Entire Database [CANNOT UNDO]
# If you want to start over for dev purposes
# Drop Database Cyio;

# Delete Indevidual Table
# Drop Table Table_Name_Here;

# Create the table to store the listing of all games
Create Table Games (
	id INT AUTO_INCREMENT PRIMARY KEY,
    title varchar(50),
    blurb varchar(400),
    about text,
	gameID varchar(50),
    creatorID varchar(50),
    thumbnailID varchar(50)
);

# Create thubnail registry table
Create Table Thumbnails (
	id INT AUTO_INCREMENT PRIMARY KEY,
    thumbnailID varchar(50),
    uri varchar(500),
    altText varchar(500)
);

# Create the user storage table
# TODO