---
layout: writeup
title: 'Dec 14: Reversing #1'
level:
difficulty:
points:
categories: []
tags: []
flag: HV15-uQEJ-4HPX-Qcau-Xvt7-NAlP

---
## Challenge

*an easy binary starter*

pull out the Nugget out of this binary.

[hv15\_ZM0qfjSu8Tf3OZDqjS74.exe](writeupfiles/hv15_ZM0qfjSu8Tf3OZDqjS74.exe)

## Solution

We get a .NET binary. We decompile the executable with ILSpy
(http://ilspy.net/) and get the following C# code:

    using Microsoft.VisualBasic;
    using Microsoft.VisualBasic.CompilerServices;
    using System;
    using System.ComponentModel;
    using System.Diagnostics;
    using System.Drawing;
    using System.Runtime.CompilerServices;
    using System.Security.Cryptography;
    using System.Text;
    using System.Windows.Forms;

    namespace hv15
    {
    	[DesignerGenerated]
    	public class Form1 : Form
    	{
    		public class GlobalVariables
    		{
    			public static string assembly = "__ERROR_HANDLER";
    		}

    		private IContainer components;

    		[AccessedThroughProperty("Button1")]
    		private Button _Button1;

    		[AccessedThroughProperty("TextBox1")]
    		private TextBox _TextBox1;

    		[AccessedThroughProperty("Label1")]
    		private Label _Label1;

    		[AccessedThroughProperty("PictureBox1")]
    		private PictureBox _PictureBox1;

    		[AccessedThroughProperty("Label2")]
    		private Label _Label2;

    		internal virtual Button Button1
    		{
    			get
    			{
    				return this._Button1;
    			}
    			[MethodImpl(MethodImplOptions.Synchronized)]
    			set
    			{
    				EventHandler value2 = new EventHandler(this.Button1_Click);
    				if (this._Button1 != null)
    				{
    					this._Button1.Click -= value2;
    				}
    				this._Button1 = value;
    				if (this._Button1 != null)
    				{
    					this._Button1.Click += value2;
    				}
    			}
    		}

    		internal virtual TextBox TextBox1
    		{
    			get
    			{
    				return this._TextBox1;
    			}
    			[MethodImpl(MethodImplOptions.Synchronized)]
    			set
    			{
    				this._TextBox1 = value;
    			}
    		}

    		internal virtual Label Label1
    		{
    			get
    			{
    				return this._Label1;
    			}
    			[MethodImpl(MethodImplOptions.Synchronized)]
    			set
    			{
    				this._Label1 = value;
    			}
    		}

    		internal virtual PictureBox PictureBox1
    		{
    			get
    			{
    				return this._PictureBox1;
    			}
    			[MethodImpl(MethodImplOptions.Synchronized)]
    			set
    			{
    				this._PictureBox1 = value;
    			}
    		}

    		internal virtual Label Label2
    		{
    			get
    			{
    				return this._Label2;
    			}
    			[MethodImpl(MethodImplOptions.Synchronized)]
    			set
    			{
    				this._Label2 = value;
    			}
    		}

    		public Form1()
    		{
    			base.Load += new EventHandler(this.Form1_Load);
    			this.InitializeComponent();
    		}

    		[DebuggerNonUserCode]
    		protected override void Dispose(bool disposing)
    		{
    			try
    			{
    				if (disposing && this.components != null)
    				{
    					this.components.Dispose();
    				}
    			}
    			finally
    			{
    				base.Dispose(disposing);
    			}
    		}

    		[DebuggerStepThrough]
    		private void InitializeComponent()
    		{
    			ComponentResourceManager componentResourceManager = new ComponentResourceManager(typeof(Form1));
    			this.Button1 = new Button();
    			this.TextBox1 = new TextBox();
    			this.Label1 = new Label();
    			this.PictureBox1 = new PictureBox();
    			this.Label2 = new Label();
    			((ISupportInitialize)this.PictureBox1).BeginInit();
    			this.SuspendLayout();
    			Control arg_70_0 = this.Button1;
    			Point location = new Point(266, 143);
    			arg_70_0.Location = location;
    			this.Button1.Name = "Button1";
    			Control arg_98_0 = this.Button1;
    			Size size = new Size(87, 25);
    			arg_98_0.Size = size;
    			this.Button1.TabIndex = 0;
    			this.Button1.Text = "Verify";
    			this.Button1.UseVisualStyleBackColor = true;
    			Control arg_DA_0 = this.TextBox1;
    			location = new Point(266, 108);
    			arg_DA_0.Location = location;
    			this.TextBox1.Name = "TextBox1";
    			Control arg_105_0 = this.TextBox1;
    			size = new Size(333, 20);
    			arg_105_0.Size = size;
    			this.TextBox1.TabIndex = 1;
    			this.Label1.AutoSize = true;
    			Control arg_137_0 = this.Label1;
    			location = new Point(263, 79);
    			arg_137_0.Location = location;
    			this.Label1.Name = "Label1";
    			Control arg_162_0 = this.Label1;
    			size = new Size(336, 14);
    			arg_162_0.Size = size;
    			this.Label1.TabIndex = 2;
    			this.Label1.Text = "Please enter the daily Code and click on Verify";
    			this.PictureBox1.Image = (Image)componentResourceManager.GetObject("PictureBox1.Image");
    			Control arg_1B0_0 = this.PictureBox1;
    			location = new Point(12, 12);
    			arg_1B0_0.Location = location;
    			this.PictureBox1.Name = "PictureBox1";
    			Control arg_1DE_0 = this.PictureBox1;
    			size = new Size(233, 156);
    			arg_1DE_0.Size = size;
    			this.PictureBox1.TabIndex = 3;
    			this.PictureBox1.TabStop = false;
    			this.Label2.AutoSize = true;
    			this.Label2.Enabled = false;
    			Control arg_22B_0 = this.Label2;
    			location = new Point(438, 154);
    			arg_22B_0.Location = location;
    			this.Label2.Name = "Label2";
    			Control arg_256_0 = this.Label2;
    			size = new Size(161, 14);
    			arg_256_0.Size = size;
    			this.Label2.TabIndex = 4;
    			this.Label2.Text = "code by HACKVent santa";
    			SizeF autoScaleDimensions = new SizeF(7f, 14f);
    			this.AutoScaleDimensions = autoScaleDimensions;
    			this.AutoScaleMode = AutoScaleMode.Font;
    			size = new Size(617, 180);
    			this.ClientSize = size;
    			this.Controls.Add(this.Label2);
    			this.Controls.Add(this.PictureBox1);
    			this.Controls.Add(this.Label1);
    			this.Controls.Add(this.TextBox1);
    			this.Controls.Add(this.Button1);
    			this.Font = new Font("Courier New", 8.25f, FontStyle.Regular, GraphicsUnit.Point, 0);
    			this.FormBorderStyle = FormBorderStyle.FixedToolWindow;
    			this.Name = "Form1";
    			this.StartPosition = FormStartPosition.CenterScreen;
    			this.Text = "HACKVent 2015";
    			((ISupportInitialize)this.PictureBox1).EndInit();
    			this.ResumeLayout(false);
    			this.PerformLayout();
    		}

    		private void Button1_Click(object sender, EventArgs e)
    		{
    			string text = this.TextBox1.Text;
    			string left = this.Encrypt(text, Form1.GlobalVariables.assembly);
    			if (Operators.CompareString(left, "zV5/UFU8PUD3N2T49IBuCwvGzCLYz39tkMZts7rfBU4=", false) == 0)
    			{
    				Interaction.MsgBox("yes, that is the key!", MsgBoxStyle.Information, "HACKVent 2015");
    			}
    			else
    			{
    				Interaction.MsgBox("nope, that is NOT the key!", MsgBoxStyle.Critical, "HACKVent 2015");
    			}
    		}

    		public string Encrypt(string input, string pass)
    		{
    			RijndaelManaged rijndaelManaged = new RijndaelManaged();
    			MD5CryptoServiceProvider mD5CryptoServiceProvider = new MD5CryptoServiceProvider();
    			string result;
    			try
    			{
    				byte[] array = new byte[32];
    				byte[] sourceArray = mD5CryptoServiceProvider.ComputeHash(Encoding.ASCII.GetBytes(pass));
    				Array.Copy(sourceArray, 0, array, 0, 16);
    				Array.Copy(sourceArray, 0, array, 15, 16);
    				rijndaelManaged.Key = array;
    				rijndaelManaged.Mode = CipherMode.ECB;
    				ICryptoTransform cryptoTransform = rijndaelManaged.CreateEncryptor();
    				byte[] bytes = Encoding.ASCII.GetBytes(input);
    				result = Convert.ToBase64String(cryptoTransform.TransformFinalBlock(bytes, 0, bytes.Length));
    			}
    			catch (Exception expr_7B)
    			{
    				ProjectData.SetProjectError(expr_7B);
    				Exception ex = expr_7B;
    				Interaction.MsgBox(ex.Message, MsgBoxStyle.OkOnly, null);
    				result = "";
    				ProjectData.ClearProjectError();
    			}
    			return result;
    		}

    		private void Form1_Load(object sender, EventArgs e)
    		{
    			this.Show();
    			this.TextBox1.Focus();
    		}
    	}
    }
{: class="language-c#"}

We see that the user input goes through an encrypt() function, and the
base64-encoding of the result must equal
`zV5/UFU8PUD3N2T49IBuCwvGzCLYz39tkMZts7rfBU4=`.
The encrypt functions performs a Rijndael encryption in ECB mode. The
key is derived from the string `"__ERROR_HANDLER"` (variable `assembly`)
by computing the MD5 hash, and using this to construct the key as
follows (variable `array` will contain the key) :

    Array.Copy(sourceArray, 0, array, 0, 16);
    Array.Copy(sourceArray, 0, array, 15, 16);

So our key is:

    assembly = "__ERROR_HANDLER"
    MD5(assembly) = E5B45EB06725D6A06F6C337C58730956
    key for encryption = E5B45EB06725D6A06F6C337C587309E5B45EB06725D6A06F6C337C5873095600

Since we know our key and our desired output, we can now reverse the
Rijndael encryption in C# to get the nugget as follows:

(This can also be done online using dotnetfiddle
(https://dotnetfiddle.net/) )

    using System;
    using System.Security.Cryptography;
    using System.Text;

    public class Program
    {
    	public static void Main()
    	{

    		string pass = "__ERROR_HANDLER";
    		string target= "zV5/UFU8PUD3N2T49IBuCwvGzCLYz39tkMZts7rfBU4=";
    		byte[] keyarray = new byte[32];

    		// Base64 decrypt target string
    		byte[] target2 = Convert.FromBase64String(target);

    		// Set our encryption/decryption key
    		MD5CryptoServiceProvider mD5CryptoServiceProvider = new MD5CryptoServiceProvider();
    		byte[] sourceArray = mD5CryptoServiceProvider.ComputeHash(Encoding.ASCII.GetBytes(pass));
    		Array.Copy(sourceArray, 0, keyarray, 0, 16);
    		Array.Copy(sourceArray, 0, keyarray, 15, 16);

    		// Set up Rijndael Decrypt
    		RijndaelManaged rijndaelManaged = new RijndaelManaged();
    		rijndaelManaged.Key = keyarray;
    		rijndaelManaged.Mode = CipherMode.ECB;
    		ICryptoTransform cryptoTransformDecrypt = rijndaelManaged.CreateDecryptor();

    		// Do it
    		byte[] result = cryptoTransformDecrypt.TransformFinalBlock(target2,0, target2.Length);
    		System.Text.Encoding encoding = new System.Text.ASCIIEncoding();

    		// print result
    		Console.WriteLine(encoding.GetString(result));
    	}
    }
{: class="language-c#"}



