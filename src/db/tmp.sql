CREATE TABLE [dbo].[_Reference6995](
	[_IDRRef] [binary](16) NOT NULL,
	[_Version] [timestamp] NOT NULL,
	[_Marked] [binary](1) NOT NULL,
	[_IsMetadata] [binary](1) NOT NULL,
	[_ParentIDRRef] [binary](16) NOT NULL,
	[_Folder] [binary](1) NOT NULL,
	[_Code] [nchar](9) NOT NULL,
	[_Description] [nvarchar](25) NOT NULL,
	[_Fld8687] [numeric](10, 0) NULL,
	[_Fld8698] [numeric](1, 0) NULL,
PRIMARY KEY CLUSTERED
(
	[_IDRRef] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]



INSERT INTO [dbo].[_Reference6995]
           ([_IDRRef]
           ,[_Marked]
           ,[_IsMetadata]
           ,[_ParentIDRRef]
           ,[_Folder]
           ,[_Code]
           ,[_Description]
           ,[_Fld8687]
           ,[_Fld8698])
     VALUES
           (0x81F3705AB67B77C011DFFBDAF3E67235
           ,0x00
           ,0x00
           ,0x876E00195BFE421211DF627D295C40B0
           ,0x01
           ,'000000052'
           ,'Крой К/П'
           ,2
           ,1)

