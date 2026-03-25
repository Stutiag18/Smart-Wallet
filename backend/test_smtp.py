import smtplib
from email.mime.text import MIMEText

# === BREVO SMTP CONFIG ===
SMTP_SERVER = "smtp-relay.brevo.com"
SMTP_PORT = 465
USERNAME = "a60773001@smtp-brevo.com"
# PASTE YOUR KEY BELOW
PASSWORD = "xsmtpsib-eefb58650ee9def4ca18fcc3f0fe9ee031486f19181c2cb165d1e3b958f891ad-izQms3PHT7RvYNDY"

def test_smtp():
    print(f"Testing connection to {SMTP_SERVER}:{SMTP_PORT} (SSL)...")
    try:
        server = smtplib.SMTP_SSL(SMTP_SERVER, SMTP_PORT)
        print("Logging in...")
        server.login(USERNAME, PASSWORD)
        print("SUCCESS: Authenticated successfully!")
        
        # Try sending a test mail to yourself
        msg = MIMEText("This is a test email to verify Brevo SMTP configuration.")
        msg["Subject"] = "Smart Wallet - SMTP Test"
        msg["From"] = USERNAME
        msg["To"] = USERNAME
        
        server.send_message(msg)
        print("SUCCESS: Test email sent successfully!")
        server.quit()
    except Exception as e:
        print(f"\nFAILED: {e}")

if __name__ == "__main__":
    test_smtp()
