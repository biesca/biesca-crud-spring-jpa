INSERT INTO biesca_task_instance.task_status(code_status, done) values ('NEW', false);
INSERT INTO biesca_task_instance.task_status(code_status, done) values ('DOING',false);
INSERT INTO biesca_task_instance.task_status(code_status, done) values ('DONE', true);
INSERT INTO biesca_task_instance.task_status(code_status, done) values ('STOPPED', false);
INSERT INTO biesca_task_instance.task_status(code_status, done) values ('REMOVED', false);

INSERT INTO biesca_task_instance.task(task_id,task_code,task_description, task_code_status, removed, created_at) VALUES (nextval('biesca_task_instance.seq_task'), 'TASK001', 'Task description', 'NEW', false, now());
