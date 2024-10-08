package com.joci;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.joci.config.ExporterConfiguration;
import com.joci.entites.ElementInstanceEntity;
import com.joci.entites.ProcessEntity;
import com.joci.entites.ProcessInstanceEntity;
import com.joci.entites.VariableEntity;
import com.joci.filters.RecordFilter;
import com.joci.mappers.EntityMapper;
import com.joci.repository.impl.ElementInstanceRepository;
import com.joci.repository.impl.ProcessInstanceRepository;
import com.joci.repository.impl.ProcessRepository;
import com.joci.repository.impl.VariableRepository;
import io.camunda.zeebe.exporter.api.Exporter;
import io.camunda.zeebe.exporter.api.context.Context;
import io.camunda.zeebe.exporter.api.context.Controller;
import io.camunda.zeebe.protocol.record.Record;
import io.camunda.zeebe.protocol.record.RecordType;
import io.camunda.zeebe.protocol.record.ValueType;
import io.camunda.zeebe.protocol.record.value.DeploymentRecordValue;
import io.camunda.zeebe.protocol.record.value.ProcessInstanceRecordValue;
import io.camunda.zeebe.protocol.record.value.VariableRecordValue;
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
                .dataSource("jdbc:sqlserver://10.10.1.244:49980;instanceName=SQL2022;databaseName=AspektExporter;trustServerCertificate=true", "jovan", "Joci123!")
                .locations("classpath:db/migration")
                .baselineOnMigrate(true)
                .loggers("slf4j")
                .validateMigrationNaming(true)
                .schemas("dbo")
                .load();

        var result = flyway.migrate();
        System.out.println("Flyway applied " + result.migrationsExecuted + " migrations.");

        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(ProcessEntity.class)
                .addAnnotatedClass(ProcessInstanceEntity.class)
                .addAnnotatedClass(ElementInstanceEntity.class)
                .addAnnotatedClass(VariableEntity.class)
                .buildSessionFactory();

        processInstanceRepository = new ProcessInstanceRepository(sessionFactory);
        processRepository = new ProcessRepository(sessionFactory);
        elementInstanceRepository = new ElementInstanceRepository(sessionFactory);
        variableRepository = new VariableRepository(sessionFactory);
    }

    @Override
    public void export(Record<?> record) {
        if(record.getRecordType() == RecordType.EVENT)
            if (record.getValueType() == ValueType.DEPLOYMENT)
                EntityMapper.ToProcessEntity((Record<DeploymentRecordValue>) record,
                        controller,
                        processRepository);
            else if (record.getValueType() == ValueType.PROCESS_INSTANCE)
                EntityMapper.ToProcessAndElementInstanceEntity((Record<ProcessInstanceRecordValue>) record,
                        controller,
                        processInstanceRepository,
                        elementInstanceRepository);
            else if (record.getValueType() == ValueType.VARIABLE)
                EntityMapper.ToVariableEntity((Record<VariableRecordValue>) record,
                        controller,
                        variableRepository);
        else
            this.controller.updateLastExportedRecordPosition(record.getPosition());
    }

    @Override
    public void close() {
        if (sessionFactory != null)
            sessionFactory.close();
    }
}
