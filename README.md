# littlepay-coding-exercise
An implementation of the coding exercise provided by LittlePay

# Assumptions

 - File is consistent and can be read into memory; ie. no transient updates
 - File is in order of ascending date and time; ie. no sort is required prior to processing
 - The software processing the tap on and tap off from the hardware uses the same expiry times as when post-processing the file
 - Services do not break down; ie. journeys are not interruped outside of the passenger's control

 # Running the Script

 The script can be run in two distinct modes:
  - utilising a duration for a valid Tap On status
  - a Tap On status does not expire

## Expiry Time

If expiry of a Tap On status is to be used, this will entail that the software utilised to handle the physical Tap On/Off would be mapped the same expiry time as the scriptprocessing; otherwise this would lead to inconsistency in the status passed to the script for fare processing.

For the processing of the expiry to be handled consistently, a processing date and time must be passed in to the script to utilise an expiry time.

## No Expiry

If no expiry is provided, then a Tap On status will remain valid indefinitely.  This entails that it will only be updated by another status or being processed as a full fare should it never be tapped off by the end of the file processing.

In a more complete system, a transfer file could be created to initialise the state of Tap On instances prior to starting the processing of a new file.

## Command Line Arguments

The script to process the fares is run from the command line using the following:

```bash
Usage: java LittlePay <filename> [process UTC: dd/MM/yyyy HH:mm:ss] [expiry time minutes]
```

### filename
This is a path and filename to the CSV file to be processed; as described in the documentation, this is expected as a well-formed file; therefore there is little included validation.

### separator
This is an optional field to specify a particular separator; the default is a comma.

### process UTC
This is the optional time and date to utilise for processing the file.  Including this option entails that an expiry time will be used for each Tap On as it relates to the UTC date and time supplied.

### expiry time minutes
This optional field allows the number of minutes a Tap On is valid before it expires.  The default is 120 minutes.
