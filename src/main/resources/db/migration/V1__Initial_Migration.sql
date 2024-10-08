create table ElementInstance (
    Id varchar(255) not null,
    BpmnElementType varchar(255),
    ElementId varchar(255),
    FlowScopeKey bigint,
    Intent varchar(255),
    Key_ bigint,
    PartitionId int,
    Position bigint,
    ProcessDefinitionKey bigint,
    ProcessInstanceKey bigint,
    Timestamp bigint,
    primary key (Id)
)

create table Process (
    Key_ bigint not null,
    BrokerVersion varchar(255),
    Intent varchar(255) not null,
    PartitionId int not null,
    Position bigint not null,
    SourceRecordPosition bigint,
    Timestamp bigint not null,
    BpmnProcessId varchar(255) not null,
    DeploymentKey bigint not null,
    Resource varbinary(max) not null,
    ResourceName varchar(255),
    Version int not null,
    primary key (Key_)
)

create table ProcessInstance (
    Key_ bigint not null,
    BrokerVersion varchar(255),
    Intent varchar(255) not null,
    PartitionId int not null,
    Position bigint not null,
    SourceRecordPosition bigint,
    Timestamp bigint not null,
    BpmnElementType varchar(255),
    BpmnEventType varchar(255),
    BpmnProcessId varchar(255),
    ElementId varchar(255),
    EndTime bigint,
    FlowScopeKey bigint,
    ParentElementInstanceKey bigint,
    ParentProcessInstanceKey bigint,
    ProcessDefinitionKey bigint,
    StartTime bigint,
    State varchar(255),
    Version int,
    primary key (Key_)
)