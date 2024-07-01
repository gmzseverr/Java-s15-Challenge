package modal;


    public abstract class Methods {
        protected static int nextUserId = 1;

        protected static int getNextUserId() {

            return nextUserId;
        }

        protected static void incrementUserId() {

            nextUserId++;
        }

        public abstract Book findBook(String input);

        public abstract boolean isEmailTaken(String email);
    }




