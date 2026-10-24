<h1 align="center">
	<br>
	<img width="320" src="https://www.pscleanair.org/ImageRepository/Document?documentId=150">
	<br>
  <br>
</h1>

# Java AQI Calculator
[![Maven Central](https://img.shields.io/maven-central/v/io.github.thanglequoc/aqi-calculator)](https://central.sonatype.com/artifact/io.github.thanglequoc/aqi-calculator)
[![CodeFactor](https://www.codefactor.io/repository/github/thanglequoc/aqi-calculator/badge)](https://www.codefactor.io/repository/github/thanglequoc/aqi-calculator)

Are you also looking for Node version ? [![npm version](https://badge.fury.io/js/aqi-bot.svg)](https://badge.fury.io/js/aqi-bot)
### Foreword
All formulas, constraints, and messages use in this project follows **US EPA AQI Calculation Guideline and Regulations** (which includes breakpoint table and calculation formulas) . Since each country issues different AQI calculation methods, using this package might be inappropriate, consider your usage.

This project is intended to community target for free use. The author is not associated with USA government, nor United States Environmental Protection Agency (a.k.a **US EPA**)

## Highlights
- Calculate AQI from raw concentration
- The result from calculation includes Air Quality Index, Category, Color, Sensitive Groups of the pollutant and the corresponding Health Effects Statements and Cautionary Statements messages.
- Support NowCast Concentration
- You need to get messages in another language ? AQI Calculator now allows customizable messages at your own need.

### Support The Following Pollutants

| Pollutant  | Scientific name| Unit of Measurement |Sensitive Groups |Regular Calculation Support |NowCast Support | Health Effects Statements | Cautionary Statements|
| ---- |:-------------:|:-------------:|:-------------:|:-------------:|-------------:|-------------:|-------------:|
| PM10      |  10 μm Particle Pollutant  | μg/m3| :heavy_check_mark: | :heavy_check_mark:| :heavy_check_mark:|:heavy_check_mark:|:heavy_check_mark:|
| PM2.5      |  2.5 μm Particle Pollutant  | μg/m3| :heavy_check_mark: |  :heavy_check_mark:| :heavy_check_mark:|:heavy_check_mark:|:heavy_check_mark:|
| O3     |  Ozone  | ppb| :heavy_check_mark: | :heavy_check_mark:| :heavy_check_mark:|:heavy_check_mark:|:heavy_check_mark:|
| CO     |  Carbon Monoxide  | ppb| :heavy_check_mark: | :heavy_check_mark:|:x:|:heavy_check_mark:|:heavy_check_mark:|
| SO2     |  Sulfur Dioxide  | ppb| :heavy_check_mark: | :heavy_check_mark:| :x:|:heavy_check_mark:|:heavy_check_mark:|
| NO2     |  Nitrogen Dioxide  | ppb| :heavy_check_mark: | :heavy_check_mark:| :x:|:heavy_check_mark:|:heavy_check_mark:|


## Installation
> **Coordinates changed in 1.3.3:** the artifact is now published as
> `io.github.thanglequoc:aqi-calculator`. The Java package remains
> `com.thanglequoc.aqicalculator`, so existing imports do not change.
>
> **Java 17+ is required starting with 1.3.3.** The AQI calculation behavior is unchanged
> from 1.3.2, but the build now targets Java 17.

## AQI Calculator as a standalone app
If you prefer to use this library as a standalone REST API app, see [AQI Calculator as a standalone app](https://github.com/thanglequoc/aqi-calculator/blob/master/README.md#aqi-calculator-as-a-standalone-app)

### With Maven
```
<!-- https://mvnrepository.com/artifact/io.github.thanglequoc/aqi-calculator -->
<dependency>
    <groupId>io.github.thanglequoc</groupId>
    <artifactId>aqi-calculator</artifactId>
    <version>1.3.3</version>
</dependency>
```
### With Gradle
```
// https://mvnrepository.com/artifact/io.github.thanglequoc/aqi-calculator
implementation group: 'io.github.thanglequoc', name: 'aqi-calculator', version: '1.3.3'
```
Or for other build tools like SBT, Ivy,.. you may find it on [Maven Central Repository](https://mvnrepository.com/artifact/io.github.thanglequoc/aqi-calculator/1.3.3)
### Building from source
JDK 17 or newer is required. The Maven wrapper is included, so Maven does not need to be installed.

```
./mvnw clean verify
```

The built jar is in `target/aqi-calculator-<version>.jar`. Release jars are attached to
[GitHub Releases](https://github.com/thanglequoc/aqi-calculator/releases).

## Quick Usage
#### For Regular AQI Calculation
##### Using AQIResult Object:
```
AQICalculator calculator = AQICalculator.getAQICalculatorInstance();
AQIResult result = calculator.getAQI(Pollutant.PM10, 99);
```

Now `AQIResult` store all the related information that you might need, query them by the following methods
* The Air Quality Index (AQI)

```result.getAQI();```
>73

* The Concentration
```result.getConcentration();```

For _NowCast_ calculation, this will be the **NowCast Concentration**
> 99.0

* The AQI Category
```result.getCategory();```
> Moderate

* The color of this index
```result.getColor()```
> Yellow

* The meaning of of this index
```result.getMeaning()```
> Air quality is acceptable; however, for some pollutants there may be a moderate health concern for a very small number of people who are unusually sensitive to air pollution

* The sensitive groups of this pollutant
```result.getSensitiveGroups()```
> People with respiratory disease are the group most at risk

* The health effects statements
`result.getHealthEffectsStatements();`
> Respiratory symptoms possible in unusually sensitive individuals; possible aggravation of heart or lung disease in people with cardiopulmonary disease and older adults

* The cautionary statements

`result.getCautionaryStatements();`
> Unusually sensitive people should consider reducing prolonged or heavy exertion


#### For NowCast AQI Calculation

~~~~
/* Example Data for NowCast PM10 12h period - 64, 63, 72, 77, 65, 61, 70, 71, 64, 57, 58, 64 */
AQICalculator calculator = AQICalculator.getAQICalculatorInstance();

double[] data = { 64, 63, 72, 77, 65, 61, 70, 71, 64, 57, 58, 64 };
AQIResult result = calculator.getNowCastAQI(Pollutant.PM10, data);
System.out.println(result.getAQI());
~~~~
>57

The first value in the array is the avg value in the current hour, and each next element in the array represent the previous data of the hour before current hour.

If the hour doesn't have data, replace missing data in the hour with **-1**
##### Example NowCast Dataset for PM10: (have some missing data in hour)
| Hour | Avg Concentration
| ---- |:-------------:|
| 14      | 64 ppb |
| 13      | 63 ppb |
| 12      | ---- |
| 11      | 77 ppb |
| 10      | 65 ppb |
| 9      | ---- |
| 8      | 70 ppb |
| 7      | 71 ppb |
| 6      | ---- |
| 5      | 57 ppb|
| 4      | 58 ppb|
| 3      | 64 ppb|

Presume that you want to calculate NowCast AQI for PM10 at **14**, the data array should be

~~~~
double[] data = { 64, 63, -1, 77, 65, -1, 70, 71, -1, 57, 58, 64 };`
AQIResult result = calculator.getNowCastAQI(Pollutant.PM10, data);
result.getAQI();
~~~~
>56

### Customize AQI Messages
The default text from AQI result is in **English**. However, you can easily override these messages in your own language. Just download the [template files](https://github.com/thanglequoc/aqi-calculator/tree/master/src/main/resources/AQIresource) and make change on your own

Allowed customized messages resources:
- General Messages 
- Sensitive Groups 
- Specific Messages

**Example**: Override with custom AQI message in German language. Simply done by enable custom message mode, and provide any of the override files path from your project classpath.
![Sample Settings](https://i.imgur.com/Q9GnIUQ.png)

~~~~
AQICalculator calculator = AQICalculator.getAQICalculatorInstance();

AQICustomSettings mySettings = new AQICustomSettings()
	.withCustomMessagesMode(true)
	.withGeneralMessageResourcePath("AQIResource/custom-aqi-general-messages_de.json")
	.withSensitiveGroupsResourcePath("AQIResource/custom-aqi-sensitive-groups_de.json")
	.withSpecificMessageResourcePath("AQIResource/custom-aqi-specific-messages_de.json");
calculator.applyCustomSettings(mySettings);

AQIResult aqiResult = calculator.getAQI(Pollutant.PM10, 99);
System.out.println(aqiResult.getCategory());
System.out.println(aqiResult.getMeaning());
System.out.println(aqiResult.getSensitiveGroups());
System.out.println(aqiResult.getHealthEffectsStatements());
System.out.println(aqiResult.getCautionaryStatements());
~~~~

Outcome
```
Mäßig
Luftqualität ist akzeptabel; Bei einigen Schadstoffen kann es jedoch zu einer mäßigen Gesundheitsgefährdung für eine sehr kleine Anzahl von Personen kommen, die ungewöhnlich empfindlich auf Luftverschmutzung reagieren
Am stärksten gefährdet sind Menschen mit Atemwegserkrankungen
Atmungssymptome bei ungewöhnlich empfindlichen Personen möglich; Mögliche Verschlimmerung von Herz- oder Lungenerkrankungen bei Personen mit kardiopulmonaler Erkrankung und älteren Erwachsenen
Ungewöhnlich sensible Menschen sollten in Betracht ziehen, längere oder schwere Belastungen zu reduzieren
```
I'm not familiar with German, these messages is just the copy-paste result from Google Translate :sheep:

#### General Messages
General Messages contains the category, meaning and color
```
[{
  "index": {
    "min": 0,
    "max": 50
  },
  "category": "<<Text in your language>>",
  "meaning": "<<Text in your language>>",
  "color": "<<Text in your language>>"
}] 
```
Download [General Messages Resource File](https://github.com/thanglequoc/aqi-calculator/blob/master/src/main/resources/AQIresource/aqi-general-messages.json) and custom it on your own. Then set this file path with  `withGeneralMessageResourcePath(<path_to_your_file>)`
```
AQICustomSettings mySettings = new AQICustomSettings()
	.withCustomMessagesMode(true)
	.withGeneralMessageResourcePath("AQIResource/custom-aqi-general-messages_de.json");
calculator.applyCustomSettings(mySettings);
```
#### Sensitive Groups
Sensitive Groups File contains list of pollutant's sensitive groups
```
[{
    "code": "PM2.5",
    "sensitiveGroups": "<<Text in your language>>"
}]
```
Download [Sensitive Groups Resource File](https://github.com/thanglequoc/aqi-calculator/blob/master/src/main/resources/AQIresource/aqi-sensitive-groups.json) and custom it on your own. Then set this file path with `withSensitiveGroupsResourcePath(<path_to_your_file>)`
```
AQICustomSettings mySettings = new AQICustomSettings()
	.withCustomMessagesMode(true)
	.withSensitiveGroupsResourcePath("AQIResource/custom-aqi-sensitive-groups_de.json");
calculator.applyCustomSettings(mySettings);
```
#### Specific Messages
Specific Messages contains Health Effects Statements and Cautionary Statements for each pollutant and each specific quality level
```
[{
    "code": "PM2.5",
    "aqiLevel": [{
        "index": {
            "min": 0,
            "max": 50
        },
        "category": "<<Text in your own language>>",
        "healthEffectsStatements": "<<Text in your own language>>",
        "cautionaryStatements": "<<Text in your own language>>"
    }]
}]
```

Download [Specific Messages Resource File](https://github.com/thanglequoc/aqi-calculator/blob/master/src/main/resources/AQIresource/aqi-specific-messages.json) and custom it on your own. Then set this file path with `withSpecificMessageResourcePath(<path_to_your_file>)`
```
AQICustomSettings mySettings = new AQICustomSettings()
	.withCustomMessagesMode(true)
	.withSpecificMessageResourcePath("AQIResource/custom-aqi-specific-messages_de.json");
calculator.applyCustomSettings(mySettings);
```
#### Reset To Default Settings
Reset the calculator to default messages path settings with `calculator.resetDefaultSettings();`


## AQI Calculator as a standalone app
There is a [AQI Calculator Spring Boot project](https://github.com/thanglequoc/aqi-calculator-spring) built upon this library to make it easy to get it up and running
### As Docker container
Fear of Java, Spring Boot, or maybe you're just too lazy to install them.  
With [Docker](https://www.docker.com/), it's super easy to get the container up and running without installing Java on your local machine. You don't need to know Java or Spring  
Give it a go:  
> docker pull thanglequoc/aqi-calculator-spring-boot:latest

The container application port is *8080*, you will need to [map your host port to the port](https://docs.docker.com/config/containers/container-networking/) *8080* of the container

Run the container with a specified port on your machine (e.g: 8080) to the application port **8080**
> docker run --name=aqi-calculator -p 8080:8080 thanglequoc/aqi-calculator-spring-boot

### As Spring Boot project
Checkout this [AQI Calculator Spring Boot repository](https://github.com/thanglequoc/aqi-calculator-spring) and run it

See the document of [AQI Calculator Spring Boot repo](https://github.com/thanglequoc/aqi-calculator-spring) for API usage


# AQI Calculation Tutorial
## US EPA AQI Breakpoint
<img src="https://image.ibb.co/gvrFc5/2017_07_20_15_22_21.png" alt="2017_07_20_15_22_21" border="0">

###### Image from Wikipedia

## Calculation Formula

The AQI is the highest value calculated for each pollutant as follows:

1. 	Identify the highest concentration among all of the monitors within each reporting area and truncate as follows:

<img width="400" src="https://image.ibb.co/je0rH5/2017_07_20_15_27_19.png" alt="2017_07_20_15_27_19" border="0">

2. 	Using US EPA AQI Breakpoint, find the two breakpoints that contain the concentration.
3. 	Using AQI Equation , calculate the index.
4. 	Round the index to the nearest integer.
 

<img width="600" src="https://image.ibb.co/n6w3VQ/2017_07_20_15_25_59.png" alt="2017_07_20_15_25_59" border="0">

### NowCast for PM and Ozone
The concentration of PM10, PM2.5 is so dynamic since wind can completely clean the air in less
than 30 minutes, or a wildfire can raise the concentration with a very fast rate in an hour. So
NowCast is introduced, it mainly focus on detect the average changing of the period hour and
perform counter balancing.

<img  width="800" src="https://image.ibb.co/gcrCqQ/2017_07_20_15_30_08.png" alt="2017_07_20_15_30_08" border="0">

#### NowCast Rules
<img width="800" src="https://image.ibb.co/ntF0c5/image.png" alt="image" border="0">

##### Handling Missing data
To compute a valid NowCast, you must have at least two of the most recent 3 hours

<img width="300" src="https://image.ibb.co/hxVYx5/image.png" alt="image" border="0">

## Extra Documents and Tools that you might needs

[Air Quality Index (AQI) Basics](https://airnow.gov/index.cfm?action=aqibasics.aqi)  

[Air Now AQI Calculator: Concentration to AQI](https://airnow.gov/index.cfm?action=resources.conc_aqi_calc)

[Air Now NowCast Calculator](https://www3.epa.gov/airnow/aqicalctest/nowcast.htm)

[Daily and Hourly AQI - PM2.5 and PM10](https://forum.airnowtech.org/t/daily-and-hourly-aqi-pm2-5-and-pm10/171)

[Daily and Hourly AQI - Ozone](https://forum.airnowtech.org/t/daily-and-hourly-aqi-ozone/170)

[US EPA AQI Brochure](https://www3.epa.gov/airnow/aqi_brochure_02_14.pdf)

[US EPA AQI Technical Assistance Document](http://www.epa.gov/airnow/aqi-technical-assistance-document-sept2018.pdf).
I am have no idea why the section **Health Effects Statements** section is not presented in their latest document guideline. I managed to kept [the one in 2016 that have this section](https://drive.google.com/file/d/10Vb0pjxgaK2U5nhrHEXe5zGDsmN9wC0n/view?usp=sharing)

[US EPA NowCast Overview](https://www3.epa.gov/airnow/ani/pm25_aqi_reporting_nowcast_overview.pdf)

###### Demonstration images for NowCast in this tutorial are from [US EPA NowCast Overview](https://www3.epa.gov/airnow/ani/pm25_aqi_reporting_nowcast_overview.pdf) document


## Releasing

Maintainers: see [`development/RELEASE_SETUP.md`](development/RELEASE_SETUP.md) for the one-time
Maven Central/JReleaser setup and the release checklist. Releases are published from the
**Release - Publish to Maven Central** GitHub Actions workflow.

#### Contributors & Supports
Without these great contribution from these guys, this repository might not be as it is today. I would like to express my gratitude to

Marcus Hylton - US EPA Airnow Specialist  
[Code Contributors](https://github.com/thanglequoc/aqi-calculator/graphs/contributors)

Proudly powered by IntelliJ IDEA from [JetBrains]( https://www.jetbrains.com/?from=aqi-calculator) for open source project
<h1 align="center">
	<br>
	<img height="150" src="https://i.imgur.com/IEREk5g.png">
	<br>
  <br>
</h1>
