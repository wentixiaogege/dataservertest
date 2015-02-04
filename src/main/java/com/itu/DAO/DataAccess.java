package com.itu.DAO;

import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.itu.bean.CloudCommand;
import com.itu.bean.Command;
import com.itu.bean.SmartMeterData;
import com.itu.util.HibernateUtil;
import com.itu.util.Log4jUtil;
import com.itu.util.DateUtils;

public class DataAccess {

	static Logger logger = Log4jUtil.getLogger(DataAccess.class);

	public static SmartMeterData frontend_getSmartMeterData(int id) {
		// TODO Auto-generated method stub
		Session s = null;
		SmartMeterData smdata1 = null;
		try {
			s = HibernateUtil.getSessionFactory().openSession();
			// from后面是对象，不是表名
			String hql = "from SmartMeterData as smdata where smdata.id=:id";// 使用命名参数，推荐使用，易读。
			Query query = s.createQuery(hql);
			query.setInteger("id", id);
			// List<SmartMeterData> list = query.list();
			smdata1 = (SmartMeterData) query.uniqueResult();
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
			hibernatecmd.setCoordinatorId(0);
			hibernatecmd.setSmartmeterId(0);
			hibernatecmd.setParam1(0);
			
			
			
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
		try {
			s = HibernateUtil.getSessionFactory().openSession();
			// from后面是对象，不是表名
			String hql = "from CloudCommand order by id desc";//
			Query query = s.createQuery(hql);
			query.setFirstResult(0);
			query.setMaxResults(1);
			// List<SmartMeterData> list = query.list();
			cloudcmd = (CloudCommand) query.uniqueResult();
			logger.info(String.format("cloudcmd is :%s",
					cloudcmd.getName()));
			//com.itu.myserver.CloudCommandProtos.CloudCommand.newBuilder().setId(cloudcmd.getId()).;
		
		} catch (Exception e) {
			logger.debug("exception:" + e);
			
		} finally {

			if (s != null)
				s.close();
		}
		/*required int32 id = 1;//
		optional int32 timestamp = 2;
		optional string name = 3;
		optional int32 dataLength = 4;
		optional int32 checked = 5;
		optional int32 coordinatorId = 6;//
		optional int32 smartmeterId = 7; //
		optional string param1 = 8;      //
*/		
		return com.itu.myserver.CloudCommandProtos.CloudCommand.newBuilder().setId(cloudcmd.getCommandId()).
				                                                              setTimestamp(DateUtils.toUnixTime(cloudcmd.getTimestamp())).
				                                                              setName(cloudcmd.getName()).
				                                                              setDataLength(cloudcmd.getDataLength()).
				                                                              setChecked(cloudcmd.getChecked()).
				                                                              setCoordinatorId(cloudcmd.getCoordinatorId()).
				                                                              setSmartmeterId(cloudcmd.getCoordinatorId()).
				                                                              setParam1(cloudcmd.getParam1()).build();
	}

	public static void localserver_postSmartmeterData(SmartMeterData smdata) {
		// TODO Auto-generated method stub
		
	}
}
