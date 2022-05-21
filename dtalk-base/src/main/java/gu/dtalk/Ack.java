package gu.dtalk;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

/**
 * 设备命令响应对象<br>
 * 此类对象将做为设备命令响应经redis服务器发送到命令发送端,
 * 数据发到redis服务器以及设备端从redis服务器收到数据的过程要经过JSON序列化和反序列化<br>
 * @author guyadong
 *
 * @param <T> 设备命令执行返回结果类型
 */
public class Ack<T> {
	private long cmdSn;
	private int deviceId;
	private String deviceMac;
	private String item;
	private T value;
	private Status status;
	private String statusMessage;
	/** 设备命令执行状态 */
	public enum Status{
		/** 设备命令成功执行完成 */
		OK,
		/** 设备端不支持的操作 */
		UNSUPPORTED,
		/** 调用出错 */
		ERROR,
		/** 
		 * 设备命令响应超时
		 */ 
		TIMEOUT,
		/** 设备命令被拒绝执行 */
		REJECTED,
		/** 设备命令开始执行 */
		ACCEPTED,
		/** 返回设备命令完成进度 */
		PROGRESS,
		/** 执行中的设备命令被取消 */
		CANCELED
	}
	public Ack() {
	}
	/**
	 * 返回状态信息
	 * @return
	 */
	public String message(){
		Preconditions.checkArgument(null != status,"status field is null");
		StringBuffer buffer = new StringBuffer(String.format("device%d@%d:%s", deviceId,cmdSn,status.name()));
		switch(status){
		case ERROR:
		case REJECTED:
			if(!Strings.isNullOrEmpty(statusMessage)){
				buffer.append(":").append(statusMessage);
			}
			break;
		case PROGRESS:
			/** 此状态下value字段如果为数字类型则被解释为完成进度(0-100) */
			if(value instanceof Number){
				buffer.append(":finished %").append(((Number)value).intValue());
			}
			if(!Strings.isNullOrEmpty(statusMessage)){
				buffer.append(":").append(statusMessage);
			}
			break;
		case TIMEOUT:
		case UNSUPPORTED:
		case OK:
		case ACCEPTED:
		case CANCELED:
		default:
			break;
		}
		return buffer.toString();
	}
	/** 返回设备命令序列号 */
	public long getCmdSn() {
		return cmdSn;
	}
	public Ack<T> setCmdSn(long cmdSn) {
		this.cmdSn = cmdSn;
		return this;
	}
	/** 返回执行设备命令的设备ID */
	public int getDeviceId() {
		return deviceId;
	}

	/**
	 * @param deviceId
	 * @return 当前{@link Ack}实例
	 */
	public Ack<T> setDeviceId(int deviceId) {
		this.deviceId = deviceId;
		return this;
	}
	/**
	 * @return 返回设备MAC地址16进制(HEX)字符串
	 */
	public String getDeviceMac() {
		return deviceMac;
	}
	/**
	 * @param deviceMac 要设置的 deviceMac
	 * @return 当前{@link Ack}实例
	 */
	public Ack<T> setDeviceMac(String deviceMac) {
		this.deviceMac = deviceMac;
		return this;
	}
	/**
	 * @return 返回响应的条目(item)路径
	 */
	public String getItem() {
		return item;
	}
	/**
	 * 设备响应的条目(item)路径
	 * @param item 要设置的 item
	 * @return 当前{@link Ack}实例
	 */
	public Ack<T> setItem(String item) {
		this.item = item;
		return this;
	}
	/** 返回设备命令执行结果对象 */
	public T getValue() {
		return value;
	}
	/**
	 * 设置设备命令执行结果对象
	 * @param value
	 * @return 当前{@link Ack}实例
	 */
	public Ack<T> setValue(T value) {
		this.value = value;
		return this;
	}
	/** 返回设备命令执行状态 */
	public Status getStatus() {
		return status;
	}
	/**
	 * 设置响应状态
	 * @param status
	 * @return 当前{@link Ack}实例
	 */
	public Ack<T> setStatus(Status status) {
		this.status = status;
		return this;
	}
	/** 
	 * 返回错误信息
	 */
	public String getStatusMessage() {
		return statusMessage;
	}
	/**
	 * 设置错误信息
	 * @param errorMessage 
	 * @return 当前{@link Ack}实例
	 */
	public Ack<T> setStatusMessage(String errorMessage) {
		this.statusMessage = errorMessage;
		return this;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Ack [cmdSn=");
		builder.append(cmdSn);
		builder.append(", deviceId=");
		builder.append(deviceId);
		builder.append(", ");
		if (deviceMac != null) {
			builder.append("deviceMac=");
			builder.append(deviceMac);
			builder.append(", ");
		}
		if (item != null) {
			builder.append("item=");
			builder.append(item);
			builder.append(", ");
		}
		if (value != null) {
			builder.append("value=");
			builder.append(value);
			builder.append(", ");
		}
		if (status != null) {
			builder.append("status=");
			builder.append(status);
			builder.append(", ");
		}
		if (statusMessage != null) {
			builder.append("errorMessage=");
			builder.append(statusMessage);
		}
		builder.append("]");
		return builder.toString();
	}

}
