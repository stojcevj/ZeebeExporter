package com.joci;

import com.joci.config.ExporterConfiguration;
import com.joci.entites.*;
import com.joci.filters.RecordFilter;
import com.joci.mappers.*;
import com.joci.repository.*;
import com.joci.repository.impl.*;
import io.camunda.zeebe.exporter.api.Exporter;
import io.camunda.zeebe.exporter.api.context.Context;
import io.camunda.zeebe.exporter.api.context.Controller;
import io.camunda.zeebe.protocol.record.Record;
import io.camunda.zeebe.protocol.record.RecordType;
import io.camunda.zeebe.protocol.record.value.*;
import org.flywaydb.core.Flyway;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@SuppressWarnings("unchecked")
public class JociExporter implements Exporter {
    private Controller controller;
    private ExporterConfiguration config;
    private SessionFactory sessionFactory;

    private ProcessInstanceRepository processInstanceRepository;
    private ProcessRepository processRepository;
    private ElementInstanceRepository elementInstanceRepository;
    private VariableRepository variableRepository;
    private TimerRepository timerRepository;
    private IncidentRepository incidentRepository;
    private JobRepository jobRepository;
    private MessageRepository messageRepository;
    private MessageSubscriptionRepository messageSubscriptionRepository;
    private ErrorRepository errorRepository;

    @Override
    public void configure(Context context){
        config = context
                .getConfiguration()
                .instantiate(ExporterConfiguration.class);

        final RecordFilter filter = new RecordFilter(config);
        context.setFilter(filter);
    }

    @Override
    public void open(Controller controller) {
        this.controller = controller;

        Flyway flyway = Flyway.configure()
                .dataSource(config.getJDBCConnection(), config.getJDBCUser(), config.getJDBCPassword())
                .locations(config.getMigrationLocation())
                .schemas(config.getMigrationSchema())
                .baselineOnMigrate(true)
                .validateMigrationNaming(true)
                .loggers("slf4j")
                .load();

        var result = flyway.migrate();
        System.out.println("Flyway applied " + result.migrationsExecuted + " migrations.");

        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .setProperty("hibernate.connection.url", config.getJDBCConnection())
                .setProperty("hibernate.connection.username", config.getJDBCUser())
                .setProperty("hibernate.connection.password", config.getJDBCPassword())
                .addAnnotatedClass(ProcessEntity.class)
                .addAnnotatedClass(ProcessInstanceEntity.class)
                .addAnnotatedClass(ElementInstanceEntity.class)
                .addAnnotatedClass(VariableEntity.class)
                .addAnnotatedClass(TimerEntity.class)
                .addAnnotatedClass(IncidentEntity.class)
                .addAnnotatedClass(JobEntity.class)
                .addAnnotatedClass(MessageEntity.class)
                .addAnnotatedClass(MessageSubscriptionEntity.class)
                .addAnnotatedClass(ErrorEntity.class)
                .buildSessionFactory();

        processInstanceRepository = new ProcessInstanceRepositoryImpl(sessionFactory);
        processRepository = new ProcessRepositoryImpl(sessionFactory);
        elementInstanceRepository = new ElementInstanceRepositoryImpl(sessionFactory);
        variableRepository = new VariableRepositoryImpl(sessionFactory);
        timerRepository = new TimerRepositoryImpl(sessionFactory);
        incidentRepository = new IncidentRepositoryImpl(sessionFactory);
        jobRepository = new JobRepositoryImpl(sessionFactory);
        messageRepository = new MessageRepositoryImpl(sessionFactory);
        messageSubscriptionRepository = new MessageSubscriptionRepositoryImpl(sessionFactory);
        errorRepository = new ErrorRepositoryImpl(sessionFactory);
    }

    @Override
    public void export(Record<?> record) {
        if (record.getRecordType() == RecordType.EVENT) {
            switch (record.getValueType()) {
                case DEPLOYMENT:
                    ProcessEntityMapper.ToProcessEntity((Record<DeploymentRecordValue>) record, controller, processRepository);
                    break;
                case PROCESS_INSTANCE:
                    ProcessAndElementInstanceEntityMapper.ToProcessAndElementInstanceEntity((Record<ProcessInstanceRecordValue>) record, controller, processInstanceRepository, elementInstanceRepository);
                    break;
                case VARIABLE:
                    VariableEntityMapper.ToVariableEntity((Record<VariableRecordValue>) record, controller, variableRepository);
                    break;
                case TIMER:
                    TimerEntityMapper.ToTimerEntity((Record<TimerRecordValue>) record, controller, timerRepository);
                    break;
                case INCIDENT:
                    IncidentEntityMapper.ToIncidentEntity((Record<IncidentRecordValue>) record, controller, incidentRepository);
                    break;
                case JOB:
                    JobEntityMapper.ToJobEntity((Record<JobRecordValue>) record, controller, jobRepository);
                    break;
                case MESSAGE:
                    MessageEntityMapper.ToMessageEntity((Record<MessageRecordValue>) record, controller, messageRepository);
                    break;
                case MESSAGE_SUBSCRIPTION:
                    MessageSubscriptionEntityMapper.ToMessageSubscriptionEntity((Record<MessageSubscriptionRecordValue>) record, controller, messageSubscriptionRepository);
                    break;
                case MESSAGE_START_EVENT_SUBSCRIPTION:
                    MessageStartEventSubscriptionMapper.ToMessageStartEventSubscriptionEntity((Record<MessageStartEventSubscriptionRecordValue>) record, controller, messageSubscriptionRepository);
                    break;
                case ERROR:
                    ErrorMapper.ToErrorEntity((Record<ErrorRecordValue>) record, controller, errorRepository);
                    break;
            }
        }else this.controller.updateLastExportedRecordPosition(record.getPosition());
    }

    @Override
    public void close() {
        if (sessionFactory != null)
            sessionFactory.close();
    }
}
