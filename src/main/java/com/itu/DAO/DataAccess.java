package com.itu.DAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.itu.bean.CloudCommand;
import com.itu.bean.Command;
import com.itu.bean.SmartMeterRecord;
import com.itu.myserver.SmartMeterDataRecordsProtos.SmartMeterDataRecord;
import com.itu.myserver.SmartMeterDataRecordsProtos.SmartMeterDataRecords;
import com.itu.util.DateUtils;
import com.itu.util.HibernateUtil;
import com.itu.util.Log4jUtil;

public class DataAccess {

	static Logger logger = Log4jUtil.getLogger(DataAccess.class);

	public static SmartMeterRecord frontend_getSmartMeterData(int id) {
		// TODO Auto-generated method stub
		Session s = null;
		SmartMeterRecord smdata1 = null;
		try {
			s = HibernateUtil.getSessionFactory().openSession();
			// from后面是对象，不是表名
			String hql = "from SmartMeterData as smdata where smdata.id=:id";// 使用命名参数，推荐使用，易读。
			Query query = s.createQuery(hql);
			query.setInteger("id", id);
			// List<SmartMeterData> list = query.list();
			smdata1 = (SmartMeterRecord) query.uniqueResult();
			logger.info(String.format("Smart Meter data IEEE address is :%s",
					smdata1.getSmIeeeAddress()));
			/*
			 * for (SmartMeterData smdata1 : list) {
			 * logger.info(String.format("Smart Meter data IEEE address is :%s",
			 * smdata1.getSmIeeeAddress())); }
			 */
		} catch (Exception e) {
			logger.debug("exception:" + e);
			// fail(e.getMessage());
		} finally {

			if (s != null)
				s.close();
		}
		return smdata1;

	}

	
	public static boolean frontend_addNewCommand(com.itu.myserver.CloudCommandProtos.CloudCommand cmd) {
		// TODO Auto-generated method stub
		Session s = null;
        //using enum or using data access
		Command tmpcmd =null;
		CloudCommand hibernatecmd = new CloudCommand();
		try {
			s = HibernateUtil.getSessionFactory().openSession();
			
			String getcmdparameters = "from Command as cmd1 where cmd1.id=:id";// 使用命名参数，推荐使用，易读。
			Query query = s.createQuery(getcmdparameters);
			query.setInteger("id", cmd.getId());			
			
			tmpcmd = (Command) query.uniqueResult();
			//setting valuse
			
			hibernatecmd.setCommandId(tmpcmd.getId());
			hibernatecmd.setDataLength(tmpcmd.getDataLength());
			hibernatecmd.setName(tmpcmd.getName());
			hibernatecmd.setChecked(0);
			hibernatecmd.setTimestamp(new Date());
			hibernatecmd.setCoordinatorId(cmd.getCoordinatorId());
			hibernatecmd.setSmartmeterId(cmd.getSmartmeterId());
			hibernatecmd.setParam1(cmd.getParam1());
			
			
			
			Transaction tran = s.beginTransaction();// 开始事物
			s.save(hibernatecmd);// 执行
			tran.commit();// 提交
			logger.info(String.format("hibernate adding new command to commandcloud table ------command name is :%s", cmd.getName()));
			/*
			 * for (SmartMeterData smdata1 : list) {
			 * logger.info(String.format("Smart Meter data IEEE address is :%s",
			 * smdata1.getSmIeeeAddress())); }
			 */
		} catch (Exception e) {
			logger.debug("hibernate adding new command to commandcloud table ------exception:" + e);
			return false;
			// fail(e.getMessage());
		} finally {

			if (s != null)
				s.close();
		}
		return true;

	}

	public static com.itu.myserver.CloudCommandProtos.CloudCommand localserver_getNewCommand() {
		Session s = null;
		CloudCommand cloudcmd = null;
		//com.itu.myserver.CloudCommandProtos.CloudCommand hibernatecmd = null;
		com.itu.myserver.CloudCommandProtos.CloudCommand.Builder cloudcommandBuilder = com.itu.myserver.CloudCommandProtos.CloudCommand.newBuilder();
		try {
			s = HibernateUtil.getSessionFactory().openSession();
			// from后面是对象，不是表名
			logger.info("localserver  getting commands here------------------------ ");
			String hql = "from CloudCommand where checked = 0 order by id asc";//
			Query query = s.createQuery(hql);
			query.setFirstResult(0);
			query.setMaxResults(1);
			// List<SmartMeterData> list = query.list();
			cloudcmd = (CloudCommand) query.uniqueResult();
			logger.info(String.format("cloudcmd is :%s",
					cloudcmd.getName()));
			
			cloudcommandBuilder= com.itu.myserver.CloudCommandProtos.CloudCommand.newBuilder().setId(cloudcmd.getCommandId()).
		             setTimestamp(DateUtils.toUnixTime(cloudcmd.getTimestamp())).
	                 setName(cloudcmd.getName()).
	                 setDataLength(cloudcmd.getDataLength()).
	                 setChecked(cloudcmd.getChecked()).
	                 setCoordinatorId(cloudcmd.getCoordinatorId()).
	                 setSmartmeterId(cloudcmd.getCoordinatorId()).
	                 setParam1(cloudcmd.getParam1());
			//setting the checked bit to 1 then send it out and updating the database
			cloudcmd.setChecked(1);
			
			
			Transaction tran = s.beginTransaction();// 开始事物
			s.save(cloudcmd);// 执行
			tran.commit();// 提交
			
			
		} catch (Exception e) {
			logger.debug("exception:" + e);
			
		} finally {

			if (s != null)
				s.close();
		}

		return cloudcommandBuilder.build();
	}
	public static com.itu.myserver.CloudCommandProtos.CloudCommands localserver_getNewCommands() {
		Session s = null;
		com.itu.myserver.CloudCommandProtos.CloudCommands cloudcmd = null;
		com.itu.myserver.CloudCommandProtos.CloudCommands.Builder cloudCommandsBuilder = com.itu.myserver.CloudCommandProtos.CloudCommands.newBuilder();
		com.itu.myserver.CloudCommandProtos.CloudCommand.Builder cloudcommandBuilder = null;
		//com.itu.myserver.CloudCommandProtos.CloudCommand hibernatecmd = null;
		try {
			s = HibernateUtil.getSessionFactory().openSession();
			// from后面是对象，不是表名
			logger.info("localserver  getting commands here------------------------ ");
			String hql = "from CloudCommand where checked = 0 order by timestamp";//
			Query query = s.createQuery(hql);
			//query.setFirstResult(0);
			//query.setMaxResults(1);
		    List<CloudCommand> list = query.list();
			
		    Transaction tran = s.beginTransaction();// 开始事物
		    
		    for (CloudCommand hibernatecloudcmd : list) {
				 logger.info(String.format("localserver getting a new command name is :%s",
						 hibernatecloudcmd.getName())); 
				 cloudcommandBuilder= com.itu.myserver.CloudCommandProtos.CloudCommand.newBuilder().setId(hibernatecloudcmd.getCommandId()).
						             setTimestamp(DateUtils.toUnixTime(hibernatecloudcmd.getTimestamp())).
					                 setName(hibernatecloudcmd.getName()).
					                 setDataLength(hibernatecloudcmd.getDataLength()).
					                 setChecked(hibernatecloudcmd.getChecked()).
					                 setCoordinatorId(hibernatecloudcmd.getCoordinatorId()).
					                 setSmartmeterId(hibernatecloudcmd.getCoordinatorId()).
					                 setParam1(hibernatecloudcmd.getParam1());
				//assembling the builder.
				 cloudCommandsBuilder.addCloudcommands(cloudcommandBuilder);
				 //update the checked bit
				 hibernatecloudcmd.setChecked(1);
				 s.save(hibernatecloudcmd);// 执行
				 tran.commit();// 提交
				 
		    }
		    //Transaction tran = s.beginTransaction();// 开始事物
			//s.save(cloudcmd);// 执行
			//tran.commit();// 提交
	
		} catch (Exception e) {
			logger.debug("exception:" + e);
			
		} finally {
			if (s != null)
				s.close();
		}
		
		return cloudCommandsBuilder.build();
	}
	public static boolean localserver_postSmartmeterRecord(SmartMeterDataRecord smRecord) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Session s = null;
        //using enum or using data access
		SmartMeterRecord hibernate_smdata = new SmartMeterRecord();
		
		try {
			logger.debug(String.format("0hibernate adding new smdata to SmartMeterData table ------ setRmsV1 is :%s", smRecord.getRmsV1()));
			s = HibernateUtil.getSessionFactory().openSession();
			
			hibernate_smdata.setSmIndex(smRecord.getSmIndex());
			hibernate_smdata.setRmsV1(smRecord.getRmsV1());
			//hibernate_smdata.setRmsV1(smRecord.getRmsV1());
			
			//hibernate_smdata.setAccumulatedEnergy(new BigDecimal(Float.toString(smdata.getAccumulatedEnergy())));
			System.out.println("here 1");
			Transaction tran = s.beginTransaction();// 开始事物
			s.save(hibernate_smdata);// 执行
			tran.commit();// 提交
			logger.info(String.format("hibernate adding new smdata to SmartMeterData table ------ setRmsV1 is :%s", smRecord.getRmsV1()));
			/*
			 * for (SmartMeterData smdata1 : list) {
			 * logger.info(String.format("Smart Meter data IEEE address is :%s",
			 * smdata1.getSmIeeeAddress())); }
			 */
		} catch (Exception e) {
			logger.debug("hibernate adding new smdata to smdata table ------exception:" + e);
			return false;
			// fail(e.getMessage());
		} finally {

			if (s != null)
				s.close();
		}
		return true;	
	}
	public static boolean localserver_postSmartmeterDataRecords(SmartMeterDataRecords smdatalist) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Session s = null;
		
		try {
			logger.debug(String.format("0hibernate adding new smdata to SmartMeterData table ------ setRmsV1 "));
			s = HibernateUtil.getSessionFactory().openSession();
			Transaction tran = s.beginTransaction();// 开始事物
			//List<com.itu.localserver.client.SmartMeterDataProtos.SmartMeterRecord> list = smdatalist.getSmartMeterRecordList();
			//List<SmartMeterRecord> hibernate_smdatalist = new ArrayList<SmartMeterRecord>();
		  /*  for (SmartMeterDataRecord smRecord : smdatalist.getSmartMeterDataRecordList()) {
		    	SmartMeterRecord hibernate_smdata = new SmartMeterRecord();
		    	hibernate_smdata.setSmIndex(smRecord.getSmIndex());
				hibernate_smdata.setRmsV1(smRecord.getRmsV1());
			    //hibernate_smdata.setAccumulatedEnergy(new BigDecimal(Float.toString(smdata.getAccumulatedEnergy())));
				
			    s.save(hibernate_smdata);// 执行
			    
			    logger.info(String.format("hibernate adding new smdata to SmartMeterData table ------ setRmsV1 is :%s", smRecord.getRmsV1()));
			
		    }	*/
		    
		    int i= 0,countnum=smdatalist.getSmartMeterDataRecordCount();
		    SmartMeterRecord[] templist = new SmartMeterRecord[countnum];
		    
		    for (SmartMeterDataRecord smRecord : smdatalist.getSmartMeterDataRecordList()) {
		    	templist[i].setSmIndex(smRecord.getSmIndex());
		    	templist[i].setRmsV1(smRecord.getRmsV1());
		    	
			    //hibernate_smdata.setAccumulatedEnergy(new BigDecimal(Float.toString(smdata.getAccumulatedEnergy())));
				i=i+1;
			    s.save(templist[i]);// 执行
			    
			    logger.info(String.format("hibernate adding new smdata to SmartMeterData table ------ setRmsV1 is :%s", smRecord.getRmsV1()));
			
		    }	
		    tran.commit();// 提交
			/*
			 * for (SmartMeterData smdata1 : list) {
			 * logger.info(String.format("Smart Meter data IEEE address is :%s",
			 * smdata1.getSmIeeeAddress())); }
			 */
		} catch (Exception e) {
			logger.debug("hibernate adding new smdata to SmartMeterData table------exception:" + e);
			return false;
			// fail(e.getMessage());
		} finally {

			if (s != null)
				s.close();
		}
		return true;	
	}
}
