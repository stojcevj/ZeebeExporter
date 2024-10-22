CREATE TABLE ElementInstance
(
    Id                   varchar(255) NOT NULL,
    Position             bigint       NOT NULL,
    SourceRecordPosition bigint,
    Timestamp            bigint       NOT NULL,
    Intent               varchar(255) NOT NULL,
    PartitionId          int          NOT NULL,
    BrokerVersion        varchar(255),
    Key_                 bigint,
    ProcessDefinitionKey bigint,
    ProcessInstanceKey   bigint,
    ElementId            varchar(255),
    BpmnElementType      varchar(255),
    FlowScopeKey         bigint,
    CONSTRAINT pk_elementinstance PRIMARY KEY (Id)
)
GO
CREATE TABLE Incident
(
    Key_                 bigint       NOT NULL,
    Position             bigint       NOT NULL,
    SourceRecordPosition bigint,
    Timestamp            bigint       NOT NULL,
    Intent               varchar(255) NOT NULL,
    PartitionId          int          NOT NULL,
    BrokerVersion        varchar(255),
    BpmnProcessId        varchar(255),
    ProcessDefinitionKey bigint,
    ProcessInstanceKey   bigint,
    ElementInstanceKey   bigint,
    JobKey               bigint,
    ErrorMessage         varchar(MAX),
    ErrorType            varchar(255),
    Created              bigint,
    Resolved             bigint,
    CONSTRAINT pk_incident PRIMARY KEY (Key_)
)
GO
CREATE TABLE Job
(
    Key_                 bigint       NOT NULL,
    Position             bigint       NOT NULL,
    SourceRecordPosition bigint,
    Timestamp            bigint       NOT NULL,
    Intent               varchar(255) NOT NULL,
    PartitionId          int          NOT NULL,
    BrokerVersion        varchar(255),
    ProcessInstanceKey   bigint,
    ElementInstanceKey   bigint,
    JobType              varchar(255),
    Worker               varchar(255),
    State                varchar(255),
    Retries              int,
    CONSTRAINT pk_job PRIMARY KEY (Key_)
)
GO
CREATE TABLE Message
(
    Key_                 bigint       NOT NULL,
    Position             bigint       NOT NULL,
    SourceRecordPosition bigint,
    Timestamp            bigint       NOT NULL,
    Intent               varchar(255) NOT NULL,
    PartitionId          int          NOT NULL,
    BrokerVersion        varchar(255),
    Name                 varchar(255),
    CorrelationKey       varchar(255),
    MessageId            varchar(255),
    Payload              varchar(MAX),
    State                varchar(255),
    CONSTRAINT pk_message PRIMARY KEY (Key_)
)
GO
CREATE TABLE MessageSubscription
(
    Id                   varchar(255) NOT NULL,
    Position             bigint       NOT NULL,
    SourceRecordPosition bigint,
    Timestamp            bigint       NOT NULL,
    Intent               varchar(255) NOT NULL,
    PartitionId          int          NOT NULL,
    BrokerVersion        varchar(255),
    MessageName          varchar(255),
    CorrelationKey       varchar(255),
    ProcessInstanceKey   bigint,
    ElementInstanceKey   bigint,
    ProcessDefinitionKey bigint,
    TargetFlowNodeId     varchar(255),
    State                varchar(255),
    BpmnProcessId        varchar(255),
    IsInterrupting       bit          NOT NULL,
    CONSTRAINT pk_messagesubscription PRIMARY KEY (Id)
)
GO
CREATE TABLE Process
(
    Key_                 bigint       NOT NULL,
    Position             bigint       NOT NULL,
    SourceRecordPosition bigint,
    Timestamp            bigint       NOT NULL,
    Intent               varchar(255) NOT NULL,
    PartitionId          int          NOT NULL,
    BrokerVersion        varchar(255),
    DeploymentKey        bigint,
    BpmnProcessId        varchar(255),
    Version              int,
    Resource             varbinary(MAX),
    ResourceName         varchar(255),
    CONSTRAINT pk_process PRIMARY KEY (Key_)
)
GO
CREATE TABLE ProcessInstance
(
    Key_                     bigint       NOT NULL,
    Position                 bigint       NOT NULL,
    SourceRecordPosition     bigint,
    Timestamp                bigint       NOT NULL,
    Intent                   varchar(255) NOT NULL,
    PartitionId              int          NOT NULL,
    BrokerVersion            varchar(255),
    BpmnProcessId            varchar(255),
    Version                  int,
    ProcessDefinitionKey     bigint,
    ElementId                varchar(255),
    FlowScopeKey             bigint,
    BpmnElementType          varchar(255),
    ParentProcessInstanceKey bigint,
    ParentElementInstanceKey bigint,
    BpmnEventType            varchar(255),
    State                    varchar(255),
    StartTime                bigint,
    EndTime                  bigint,
    CONSTRAINT pk_processinstance PRIMARY KEY (Key_)
)
GO
CREATE TABLE Timer
(
    Key_                 bigint       NOT NULL,
    Position             bigint       NOT NULL,
    SourceRecordPosition bigint,
    Timestamp            bigint       NOT NULL,
    Intent               varchar(255) NOT NULL,
    PartitionId          int          NOT NULL,
    BrokerVersion        varchar(255),
    ProcessDefinitionKey bigint,
    ProcessInstanceKey   bigint,
    ElementInstanceKey   bigint,
    TargetElementId      varchar(255),
    DueDate              bigint,
    State                varchar(255),
    Repetitions          int,
    CONSTRAINT pk_timer PRIMARY KEY (Key_)
)
GO
CREATE TABLE Variable
(
    Id                   varchar(255) NOT NULL,
    Position             bigint       NOT NULL,
    SourceRecordPosition bigint,
    Timestamp            bigint       NOT NULL,
    Intent               varchar(255) NOT NULL,
    PartitionId          int          NOT NULL,
    BrokerVersion        varchar(255),
    ProcessInstanceKey   bigint,
    ProcessDefinitionKey bigint,
    Name                 varchar(255),
    Value                varchar(MAX),
    ScopeKey             bigint,
    BpmnProcessId        varchar(255),
    State                varchar(255),
    CONSTRAINT pk_variable PRIMARY KEY (Id)
)
CREATE TABLE Error
(
    Id                   bigint       NOT NULL,
    Position             bigint       NOT NULL,
    SourceRecordPosition bigint,
    Timestamp            bigint       NOT NULL,
    Intent               varchar(255) NOT NULL,
    PartitionId          int          NOT NULL,
    BrokerVersion        varchar(255),
    ErrorEventPosition   bigint,
    ProcessInstanceKey   bigint,
    ExceptionMessage     varchar(MAX),
    StackTrace           varchar(255),
    CONSTRAINT pk_error PRIMARY KEY (Id)
)
GO