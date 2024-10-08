create table Variable (
    Id varchar(255) not null,
    Position bigint not null,
    PartitionId int not null,
    Name varchar(255),
    Value varchar(255),
    ScopeKey bigint,
    ProcessInstanceKey bigint,
    ProcessDefinitionKey bigint,
    BpmnProcessId varchar(255),
    Timestamp bigint not null,
    Intent varchar(255) not null,
    State varchar(255),
    BrokerVersion varchar(255),
    SourceRecordPosition bigint,
    primary key (Id)
)