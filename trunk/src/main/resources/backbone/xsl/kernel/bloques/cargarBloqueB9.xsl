<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="/">
		<resultado-dao xsi:type="java:mx.com.ice.kernel.to.ResultadoDAO">
			<xsl:choose>
				<xsl:when test="result/storedProcedure/outparam[@id='po_TIPOERROR']/@value='S'">
					<xsl:apply-templates select="result/storedProcedure/outparam/rows"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:call-template name="error"/>
				</xsl:otherwise>
			</xsl:choose>
			<xsl:element name="estado">
				<xsl:value-of select="result/storedProcedure/outparam[@id='po_TIPOERROR']/@value"/>
			</xsl:element>
		</resultado-dao>
	</xsl:template>
	<xsl:template match="rows">
		<xsl:for-each select="row">
			<cursor xsi:type="java:mx.com.ice.kernel.to.ResultadoDAO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
					<xsl:if test="@CDUNIECO">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								CDUNIECO
							</key>
							<value xsi:type="java:java.lang.Integer">
								<xsl:value-of select="@CDUNIECO"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@CDRAMO">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								CDRAMO
							</key>
							<value xsi:type="java:java.lang.Integer">
								<xsl:value-of select="@CDRAMO"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@ESTADO">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								ESTADO
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@ESTADO"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@NMPOLIZA">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								NMPOLIZA
							</key>
							<value xsi:type="java:java.lang.Long">
								<xsl:value-of select="@NMPOLIZA"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@NMSITUAC">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								NMSITUAC
							</key>
							<value xsi:type="java:java.lang.Long">
								<xsl:value-of select="@NMSITUAC"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@CDCAPITA">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								CDCAPITA
							</key>
							<value xsi:type="java:java.lang.Integer">
								<xsl:value-of select="@CDCAPITA"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@NMSUPLEM">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								NMSUPLEM
							</key>
							<value xsi:type="java:java.lang.Long">
								<xsl:value-of select="@NMSUPLEM"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@STATUS">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								STATUS
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@STATUS"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@AADESDE">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								AADESDE
							</key>
							<value xsi:type="java:java.lang.Integer">
								<xsl:value-of select="@AADESDE"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@AAHASTA">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								AAHASTA
							</key>
							<value xsi:type="java:java.lang.Integer">
								<xsl:value-of select="@AAHASTA"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@POIMPREV">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								POIMPREV
							</key>
							<value xsi:type="java:java.lang.Long">
								<xsl:value-of select="@POIMPREV"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@SWAUTOMA">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								SWAUTOMA
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@SWAUTOMA"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@CDTIPREV">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								CDTIPREV
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@CDTIPREV"/>
							</value>
						</values>
					</xsl:if>
			</cursor>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="error">
		<xsl:element name="cd-error">
			<xsl:value-of select="result/storedProcedure/outparam[@id='po_CDERROR']/@value"/>
		</xsl:element>
		<xsl:element name="ds-error">
			<xsl:value-of select="result/storedProcedure/outparam[@id='po_DSERROR']/@value"/>
		</xsl:element>
	</xsl:template>
</xsl:stylesheet>
