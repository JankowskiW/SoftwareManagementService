# User Management Service

The Software Management Service oversees the management of applications and their versions, 
providing capabilities to retrieve, create, update, and delete both applications and their versions, 
as well as to manage the relationships between them.

## Table of contents
1. [Functionality Overview](#functionality-overview)
    1. [Application module](#application-module)
    2. [ApplicationVersion module](#applicationversion-module)
    3. [Additional notes](#additional-notes)
2. [Application Startup](#application-startup)
    1. [Prerequisites](#prerequisites)
    2. [Application cloning](#application-cloning)
    3. [Containers startup](#containers-startup)
    4. [Additional information](#additional-information)
3. [Testing](#testing)
    1. [Manual Testing](#manual-testing)
    2. [Unit Testing](#unit-testing)
    3. [Integration Testing](#integration-testing)
4. [Deployment](#deployment)
5. [Monitoring](#monitoring)
6. [Logs](#logs)
7. [Environment variables](#environment-variables)
8. [Common known problems and debugging](#common-known-problems-and-debugging)
9. [Future plans](#future-plans)
10. [Changelog](#changelog)
11. [Useful materials](#useful-materials)

## Functionality Overview

### Application module

**Module description**

The Application module is responsible for managing all aspects related to applications 
and their versions, specifically those version operations that are logically connected to 
applications. This includes creating, retrieving, updating, and deleting application data, 
as well as adding and retrieving application versions.

**API Endpoints**:
- `[GET] /applications/{id}`: Retrieve details about specific application.
- `[GET] /applications`: Retrieve a paginated list of applications.
- `[POST] /applications`: Create a new application.
- `[PUT] /applications/{id}`: Fully update a specific application.
- `[PATCH] /applications/{id}`: Partially update a specific application.
- `[DELETE] /applications/{id}`: Delete a specific application.
- `[PATCH] /applications/{id}/application-versions`: Add a version to a specific application.
- `[GET] /applications/{id}/application-versions`: Retrieve a list of versions for a specific application.

### ApplicationVersion module

**Module description**

The ApplicationVersion module is responsible for handling operations related to versions management.
This includes retrieving and deleting version.

**API Endpoints**:
- `[GET] /application-versions/{id}`: Retrieve details about specific version.
- `[DELETE] /application-versions/{id}`: Delete a specific version.

### Additional notes

- **Get Applications**:
    - **Required Parameters**: The parameters pageNumber, pageSize, and sort are required, but if not provided,
      they default to `pageNumber = 1`, `pageSize = 25`, and `sort=id,asc`.
    - **Optional Filters**: You may provide filter parameters such as `name`, `productOwnerId` and `assigneeId`,
      though they are not required.
      Additional filter parameters can be introduced in the future as needed.
- **Create Application**:
    - **Data Requirements**: All necessary data must be provided to create a new user.
    - **Application Version**: To create new version of application you should use separated request that is
      managed by **ApplicationController::addVersionToApplication** method.
    - **Application archiving**: You can not create new application that is archived from the beginning.
- **Fully Update Application**:
    - **HTTP Method**: Use the HTTP PUT method to fully update a application, submitting all data,
      including fields that are not being changed, as per REST specifications.
    - **Data Requirements**: All required data (same as for creating a new application) must be provided.
    - **Non-Existence Handling**: If the specified application ID does not exist, a new application will not be created,
      an exception will be thrown instead.
    - **Application archiving**: When the **archived** field is set to **true**, the **archivedAt** and **archivedBy** 
      fields will be updated in method annotated as **@PreUpdate** to reflect the current values of **updatedAt** 
      and **updatedBy**.
- **Partially Update Application**:
    - **HTTP Method**: Use the HTTP PATCH method to partially update a application.
    - **Data Requirements**: Only the data you wish to update needs to be provided.
    - **Application archiving**: When the **archived** field is set to **true**, the **archivedAt** and **archivedBy**
      fields will be updated in method annotated as **@PreUpdate** to reflect the current values of **updatedAt**
      and **updatedBy**.
    - **ApplicationMerger**: he ApplicationMerger is designed to update only the desired fields of the Application entity. 
      Please note that some fields, despite having the same data types, exhibit different behaviors:
      - **Name**: The name must not be null, empty, or blank in order to update.
      - **Description**: The description must not be null, empty, or blank in order to update.
      - **RepositoryUrl**: The repository URL must not be null, empty, or blank in order to update.
      - **DocumentationUrl**: The documentation URL must not be null. 
        If the documentation URL is empty, it signifies the intent to remove it from the entity.
      - **BusinessOwnerId**: The business owner ID must not be null. 
        If the ID is set to 0, it indicates that no business owner is assigned to the application.
      - **AssigneeId**: The assignee ID must not be null. If set to 0, 
        it means no developer (assignee) is responsible for the application.
      - **CurrentVersion**: The current version must not be null. If left empty, 
        it indicates that the application has no current version yet.
      - **Archived**: The archived flag must not be null. 
        You can set it to true or false to either archive the application or restore it.
- **Delete Application**:
    - **Cascade Deletion**: Deleting an application will also remove all associated application 
      versions from the **application_versions** table.
    - **Admin privileges**: Ensure you intend to delete the application, as deleted records cannot be recovered.
      Admin privileges are required for this operation.
    - **Application archiving**: Archiving applications is recommended over deletion, as it preserves data for future reference.
  - **Application Archiving**:
      - **Alternative to Deletion**: It is recommended to archive applications rather than delete them to 
        prevent unintended data loss.
- **Application Versions**:
  - **Version operations linked to application**: Some version operations are tightly linked to applications, 
    and their endpoints are located in the Application Controller. These operations are only meaningful 
    within the context of a specific application.
  - **General version operations**: There are also operations that function independently of the application context, 
    such as deleting a version by its ID or retrieving version details.

## Application Startup

### Prerequisites
Before you begin, make sure you have the following tools installed:
- Docker
- Docker Compose
- Git
- Maven (3.9.7 +)

### Application cloning
If you do not have cloned repository yet, use following command:
  ```
  git clone git@github.com:JankowskiW/SoftwareManagementService.git
  ```

### Building application
To build the application using Maven, run the following command:
```
mvn clean test package
```

### Containers startup
1. Create **.env** file based on the example below and configure the environment variables:
```
  # Application settings
  SOFTWARE_MS__PORT=8080
  SOFTWARE_MS__ACTIVE_PROFILE=dev,postgres
  SOFTWARE_MS__LOG_MAX_FILE_SIZE=10MB
  SOFTWARE_MS__LOG_MAX_HISTORY=90
  SOFTWARE_MS__TOTAL_SIZE_CAP=3GB
  
  
  # Database connection settings
  UC__DB_HOST=localhost
  UC__DB_PORT=1234
  UC__DB_DATABASE=update_center
  UC__DB_USERNAME=username
  UC__DB_PASSWORD=password
```
2. Work in progress...


### Additional information

#### Spring Profiles

To run the application correctly, you must specify **two types of active Spring profiles**.

1. **Environment Profiles**: These profiles define the environment in which the application will run
    - Development environment:
    ```
        dev
    ```
    - Production environment:
    ```
        prod
    ```

2. **Database Engine Profiles**: These profiles define the database engine you wish to use:
    - PostgreSQL:
    ```
        postgresql
    ```
    - MS SQL:
    ```
        mssql
    ```

You need to select **one profile from each category**, for example:
```
    dev,postgresql
    
    prod,mssql
    
    ...
```

#### Profile Descriptions
Environment Profiles:
1. **dev**
    - Intended for use in development environments only.
    - Enables Swagger for interactive API documentation.
2. **prod**
    - Intended for use in production environments with stable application versions.
    - Disables Swagger for security and performance reasons.

Database Engine Profiles:
1. **postgresql**
    - Connects to a PostgreSQL database.
    - Ensure the PostgreSQL server is running on the specified host and port.
2. **mssql**
    - Connects to an MS SQL (SQL Server) database.
    - Ensure the SQL Server is running on the specified host and port.


## Testing

### Manual Testing
You can manually test User Management Service using the Swagger tool, available at:
```
http://localhost:8080/swagger-ui/index.html
```
Please note that Swagger is accessible only when the application is running in the **dev**
profile to minimize security vulnerabilities.

### Unit Testing
To execute Unit Tests using Maven, run the following command:
```
mvn test
```
By default, this command runs test from /path/to/project/test/java directory.

### Integration Testing
Work in progress...

## Deployment
Work in progress...

## Monitoring
Work in progress...

## Logs
The service uses the Logback implementation of the SLF4J interface.

The configuration file is located in the resources folder and is named logback.xml.

Logback employs the RollingFileAppender with a SizeAndTimeBasedRollingPolicy,
which means log files are archived daily and whenever the specified maximum
file size is exceeded.

**Default configuration is:**
```
maxFileSize=10MB
maxHistory=90 // days
totalSizeCap=3GB
```

You can also set these values by using environment variables:
- **SOFTWARE_MS__LOG_MAX_FILE_SIZE**: for **maxFileSize** property
- **SOFTWARE_MS__MAX_HISTORY**: for **maxHistory** property (in days)
- **SOFTWARE_MS__TOTAL_SIZE_CAP**: for **totalSizeCap** property

Additionally, you can control the log level by switching between active profiles. <br/>
For the **dev** profile, the log level will be set to **debug**,
while for the **prod** profile, it will be set to **info**.

## Environment variables
Here are all the environment variables used by the UserManagementService application:

1. **UC__DB_DATABASE**:<br/>
   The system database name.<br/><br/>

2. **UC__DB_HOST:<br/>
   The system database host.<br/><br/>

3. **UC__DB_PORT**:<br/>
   The system database port.<br/><br/>

4. **UC__DB_USERNAME**:<br/>
   The username for the system database user.<br/><br/>

5. **UC__DB_PASSWORD**:<br/>
   The password for the system database user.<br/><br/>

6. **SOFTWARE_MS__ACTIVE_PROFILES**:<br/>
   The active profiles for the Software Management Service.<br/><br/>

7. **SOFTWARE_MS__PORT**:<br/>
   The port for the User Management Service.<br/><br/>

8. **SOFTWARE_MS__LOG_MAX_FILE_SIZE**:<br/>
   The maximum file size of a single log file for the Software Management Service.<br/><br/>

9. **SOFTWARE_MS__MAX_HISTORY**:<br/>
   The number of days that logs will be stored for the Software Management Service.<br/><br/>

10. **SOFTWARE_MS__TOTAL_SIZE_CAP**:<br/>
    The total capacity of all stored log files for the Software Management Service.


Variables with names starting with **UC** are common variables
used by multiple services in the system.

## Common known problems and debugging
Work in progress...

## Future plans
1. Implement functionality for collecting and displaying applications statistics. 
2. Introduce applications error rate tracking. 
3. Add support for version downgrades.

## Changelog

### Version 1.0.0
- Added **Application Module** operations, including:
  - Fetching application details.
  - Retrieving a paginated list of applications.
  - Creating new applications.
  - Fully and partially updating applications.
  - Deleting applications.
- Added **ApplicationVersion Module** operations linked to applications, such as:
  - Adding a version to an application.
  - Fetching application versions.
- Added **ApplicationVersion Module** operations independent of specific applications, including:
  - Fetching version details.
  - Deleting application versions.
  
## Useful materials
Work in progress...