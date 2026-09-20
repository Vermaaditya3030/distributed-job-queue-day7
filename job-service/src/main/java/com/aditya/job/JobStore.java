package com.aditya.job;
import com.fasterxml.jackson.databind.ObjectMapper; import org.springframework.data.redis.core.StringRedisTemplate; import org.springframework.stereotype.Service; import java.util.*;
@Service public class JobStore {
 private final StringRedisTemplate redis; private final ObjectMapper mapper;
 public JobStore(StringRedisTemplate redis,ObjectMapper mapper){this.redis=redis;this.mapper=mapper;}
 public Job create(CreateJobRequest r){String id="JOB-"+UUID.randomUUID().toString().substring(0,8); long now=System.currentTimeMillis(); Job j=new Job(id,r.type(),r.payload(),"QUEUED",0,now,now); save(j); redis.opsForList().rightPush("jobs:queue",id); return j;}
 public Optional<Job> find(String id){try{String v=redis.opsForValue().get("job:"+id);return v==null?Optional.empty():Optional.of(mapper.readValue(v,Job.class));}catch(Exception e){throw new RuntimeException(e);}}
 public Job update(Job j){save(j);return j;}
 public Optional<Job> claim(){String id=redis.opsForList().leftPop("jobs:queue");return id==null?Optional.empty():find(id);}
 public void requeue(String id){redis.opsForList().rightPush("jobs:queue",id);}
 public void dead(String id){redis.opsForList().rightPush("jobs:dead",id);}
 private void save(Job j){try{redis.opsForValue().set("job:"+j.id(),mapper.writeValueAsString(j));}catch(Exception e){throw new RuntimeException(e);}}
}
