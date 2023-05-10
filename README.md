# littlepay-coding-exercise
An implementation of the coding exercise provided by LittlePay

# Assumptions

 - File is of fixed length; ie. no transient updates
 - File is consistent and can be read into memory
 - File is in order of ascending date and time; ie. no sort is required prior to processing
 - Given the TapType is provided in the data passed to the script, another process must control the state of a PAN; ie. external software processing the card uses expiry times to denote a default tap off
 - Services do not break down; ie. journeys are not interruped and a passenger always has the option to tap off and can therefore fairly be charged a full fare
 - The file is an autonomous set of data; ie. there is no expected pre-status of a PAN and there are no PANs tap states to be created for another, or subsequent, file to process
 - The data being read in does not require validation; if there were to be validation, then it would be added to the CSVReader and ignore any lines with failed data parsing.

# Building the Script

## Command Line Arguments
The java source files are contained within the main directory and the JUnit test files are located in a ```test``` subdirectory.  A separate ```bin``` directory is utilised for storing the class files.

The script and tests can be compiled with:
```bash
javac -classpath .:bin:junit-platform-console-standalone-1.9.3.jar -sourcepath test -d bin *.java test/*Test.java
```

# Running the Tests

## Command Line Arguments
From the main directory, the tests can be executed with the following command:
```bash
java -jar junit-platform-console-standalone-1.9.3.jar --class-path bin --scan-class-path
```

# Running the Script

## Command Line Arguments

The script to process the fares is run from the command line using the following:

```bash
java -cp bin LittlePay <taps CSV input filename> [separator] [trips CSV output filename]
```

#### ```taps CSV input filename```
This is a path and filename to the CSV file to be processed; as described in the documentation, this is expected as a well-formed file; therefore there is no data validation on the fields read in.

#### ```separator```
This is an optional field to specify a particular separator; the default is a comma.

#### ```trips CSV output filename```
An optional filename for the output CSV file within an existing path.  The default is ```trips.csv```.

### Error Output
Any fatal errors from the script will exit with an error message and a related status code.

Runtime, non-fatal, errors will be output to ```stderr``` during processing.

# Fare Processing

The script will calculate the appropriate fares between stops; the following conditions are also processed:

## A tap on while already tapped on
This can only occur if, as stated in the assumptions, an external process is determining a time-out or expiry of the tap status.  Therefore, the previous tap on is charged at full fare.

## A tap off while not being tapped on
This entails an issue in the status handling of the external software providing the data, so an error will be raised.

## No tap off after file processing
As this is an autonomous process, the remaining PANs that are currently tapped on will be charged a full fare.

# Design decisions
The FareProcessor has been developed for an efficient lookup, entailing an expanded initial data configuration.  This is to facilitate longer single setup but a quicker locating of the iterative fare lookup; as the data is determined to be well-formed in the source file, there is no additional validation on an invalid stop id being passed in.

In a more expandable design, the data would not be stored internally but in a separate data source.

## Notes
The exercise states that the tap on and off is at the bus stop but the data contains a bus id, which does not really correlate as the tap on and off would have to be on the bus itself to provide that data.
