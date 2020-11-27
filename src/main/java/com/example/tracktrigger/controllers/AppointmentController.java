package com.example.tracktrigger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tracktrigger.jobs.EmailJob;
import com.example.tracktrigger.models.ApplicationUser;
import com.example.tracktrigger.models.Appointment;
import com.example.tracktrigger.payloads.ScheduleEmailRequest;
import com.example.tracktrigger.payloads.ScheduleEmailResponse;
import com.example.tracktrigger.repositories.ApplicationUserRepository;
import com.example.tracktrigger.repositories.AppointmentRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.json.JSONObject;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/demo/appt")
public class AppointmentController {
	@Autowired 
	private ApplicationUserRepository applicationUserRepository;
	@Autowired 
	private AppointmentRepository appointmentRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(EmailJobSchedulerController.class);
	@Autowired
	private Scheduler scheduler;
	
    @PostMapping(path="/create")
    public ResponseEntity <ScheduleEmailResponse> addAppt(Authentication authentication, 
    		@RequestParam String appt_name, @RequestParam String appt_desc,
    		@RequestParam String appt_reqs, @RequestParam String appt_location, 
    		@RequestParam String appt_date_time) {
    	
    	LocalDateTime date_time = LocalDateTime.parse(appt_date_time);
    	String json = authentication.getName();
    	json = json.replaceAll("=", ":");
    	JSONObject obj = new JSONObject(json);
    	String username = obj.getString("sub");
    	ApplicationUser user = applicationUserRepository.findByUsername(username);
    	
    	Long user_id = user.getId();
    	Appointment appt = new Appointment();
    	appt.setUserId(user_id);
    	appt.setApptName(appt_name);
    	appt.setApptDesc(appt_desc);
    	appt.setApptReqs(appt_reqs);
    	appt.setApptLoc(appt_location);
    	appt.setDateTime(date_time);
    	
    	
    	//Creating email reminder
    	String subject = "Reminder: " + appt_name;
    	String body = "Dear " + user.getName() + ", <br> You have an Appointment today. <br>" 
    	+ "Appointment Name: " + appt_name + "Appointment Description: " + appt_desc;
    	
    	LocalDateTime email_time = date_time.minus(Duration.ofHours(1));
    	
    	ScheduleEmailRequest scheduleEmailRequest = new ScheduleEmailRequest();
    	scheduleEmailRequest.setEmail(user.getEmail());
    	scheduleEmailRequest.setSubject(subject);
    	scheduleEmailRequest.setBody(body);
    	scheduleEmailRequest.setDateTime(email_time);
    	scheduleEmailRequest.setTimeZone(ZoneId.of("Asia/Kolkata"));
    	
        try {
            ZonedDateTime dateTime = ZonedDateTime.of(scheduleEmailRequest.getDateTime(), scheduleEmailRequest.getTimeZone());
            if(dateTime.isBefore(ZonedDateTime.now())) {
            	appointmentRepository.save(appt);
                ScheduleEmailResponse scheduleEmailResponse = new ScheduleEmailResponse(false,
                        "Appointment saved- but no email reminder set (In less than an hour)");
                return ResponseEntity.ok(scheduleEmailResponse);
            }

            JobDetail jobDetail = buildJobDetail(scheduleEmailRequest);
            Trigger trigger = buildJobTrigger(jobDetail, dateTime);
            String appt_trigger_name = jobDetail.getKey().getName();
            appt.setApptTriggerName(appt_trigger_name);
            appointmentRepository.save(appt);
            scheduler.scheduleJob(jobDetail, trigger);

            ScheduleEmailResponse scheduleEmailResponse = new ScheduleEmailResponse(true,
                    jobDetail.getKey().getName(), jobDetail.getKey().getGroup(), "Appointment Saved and Email Scheduled Successfully!");
            return ResponseEntity.ok(scheduleEmailResponse);
        } catch (SchedulerException ex) {
            logger.error("Appointment saved but Error scheduling email", ex);

            ScheduleEmailResponse scheduleEmailResponse = new ScheduleEmailResponse(false,
                    "Appointment saved but Error scheduling email. Please try later!");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(scheduleEmailResponse);
        }
    	
    	
    }
    
    @GetMapping (path="/read")
    public ArrayList <Appointment> readAppts(Authentication authentication){
    	String json = authentication.getName();
    	json = json.replaceAll("=", ":");
    	JSONObject obj = new JSONObject(json);
    	String username = obj.getString("sub");
    	ApplicationUser user = applicationUserRepository.findByUsername(username);
    	
    	Long user_id = user.getId();
    	return appointmentRepository.findByUserId(user_id);
    	
    }
    
    @GetMapping(path = "/readone")
    public Appointment readAppt(@RequestParam Long id) {
    	Appointment appt = appointmentRepository.findByApptId(id);
    	return appt;
    }
    
    @PostMapping (path = "/update")
    public ResponseEntity <String> updateAppt(@RequestParam Long id, 
    		@RequestParam String appt_name, @RequestParam String appt_desc,
    		@RequestParam String appt_reqs, @RequestParam String appt_location, 
    		@RequestParam LocalDateTime dateTime){
    	
    	Appointment appt = appointmentRepository.findByApptId(id);
    	appt.setApptDesc(appt_desc);
    	appt.setApptLoc(appt_location);
    	appt.setApptName(appt_name);
    	appt.setApptReqs(appt_reqs);
    	appt.setDateTime(dateTime);
    	appointmentRepository.save(appt);
    	return new ResponseEntity <String>("Deleted successfully", HttpStatus.OK);
    }
    
    @DeleteMapping(path = "/delete")
    public ResponseEntity <String> deleteAppt(@RequestParam Long id){
    	Appointment appt = appointmentRepository.findByApptId(id);
    	ZonedDateTime dateTime = ZonedDateTime.of(appt.getDateTime(), ZoneId.of("Asia/Kolkata"));
    	dateTime = dateTime.minus(Duration.ofHours(1));
        /*if(!(dateTime.isBefore(ZonedDateTime.now()))) {
        	scheduler.unscheduleJob(triggerKey((appt.getApptTriggerKey(), "email-triggers")));
        
        } */
    	appointmentRepository.deleteById(id);
    	
    	return new ResponseEntity <String> ("Deleted successfully!", HttpStatus.OK);
    }
    
    private JobDetail buildJobDetail(ScheduleEmailRequest scheduleEmailRequest) {
        JobDataMap jobDataMap = new JobDataMap();

        jobDataMap.put("email", scheduleEmailRequest.getEmail());
        jobDataMap.put("subject", scheduleEmailRequest.getSubject());
        jobDataMap.put("body", scheduleEmailRequest.getBody());

        return JobBuilder.newJob(EmailJob.class)
                .withIdentity(UUID.randomUUID().toString(), "email-jobs")
                .withDescription("Send Email Job")
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    private Trigger buildJobTrigger(JobDetail jobDetail, ZonedDateTime startAt) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "email-triggers")
                .withDescription("Send Email Trigger")
                .startAt(Date.from(startAt.toInstant()))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();
    }
    

}
