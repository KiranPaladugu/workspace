package com.tcs.tools;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSubsystem;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.tcs.tools.ConnectionData.ConnectionType;

public class ConnectionFactory {

	public static Channel getChannel(final ConnectionType channelType, final Session session, final String option) throws JSchException {
		switch (channelType) {
		case subsystem:
			return getSubsystemChannel(session, option);
		case shell:
			return getShellChannel(session);
		default:
			break;
		}
		return null;
	}

	public static Channel getShellChannel(final Session session) throws JSchException {
		final ChannelExec channel = (ChannelExec) session.openChannel("exec");
		return channel;
	}

	public static ChannelSubsystem getSubsystemChannel(final Session session, final String subsystem) throws JSchException {
		final ChannelSubsystem channel = (ChannelSubsystem) session.openChannel(ConnectionType.subsystem.toString());
		channel.setSubsystem(subsystem);
		return channel;
	}

}
