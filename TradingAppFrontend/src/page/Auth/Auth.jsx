import React from "react";
import { Button } from "@/components/ui/button";
import { useLocation, useNavigate } from "react-router-dom";
import SignupForm from "./SignupForm";
import ForgotPasswordForm from "./ForgotPasswordForm";
import SigninForm from "./SigninForm";
import { motion } from "framer-motion";
import { TrendingUp } from "lucide-react";

const Auth = () => {
  const navigate = useNavigate();
  const location = useLocation();

  const renderForm = () => {
    switch (location.pathname) {
      case "/signup":
        return (
          <motion.section
            className="w-full"
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.5 }}
          >
            <SignupForm />
            <div className="flex items-center justify-center mt-6 text-sm">
              <span className="mr-1 text-gray-300">
                Already have an account?
              </span>
              <Button
                onClick={() => navigate("/signin")}
                variant="link"
                className="text-primary hover:text-primary/80"
              >
                Sign In
              </Button>
            </div>
          </motion.section>
        );
      case "/forgot-password":
        return (
          <motion.section
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.5 }}
          >
            <ForgotPasswordForm />
          </motion.section>
        );
      default:
        return (
          <motion.section
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.5 }}
          >
            <SigninForm />
            <div className="flex flex-col items-center justify-center mt-6 space-y-3">
              <div className="flex items-center text-sm">
                <span className="mr-1 text-gray-300">
                  Don't have an account?
                </span>
                <Button
                  onClick={() => navigate("/signup")}
                  variant="link"
                  className="text-primary hover:text-primary/80"
                >
                  Sign Up
                </Button>
              </div>
              <Button
                onClick={() => navigate("/forgot-password")}
                variant="link"
                className="text-primary hover:text-primary/80 text-sm"
              >
                Forgot Password?
              </Button>
            </div>
          </motion.section>
        );
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-gray-900 to-black">
      <div className="w-full max-w-md">
        <motion.div
          className="bg-gray-800 shadow-2xl rounded-lg overflow-hidden"
          initial={{ scale: 0.9, opacity: 0 }}
          animate={{ scale: 1, opacity: 1 }}
          transition={{ duration: 0.5 }}
        >
          <div className="px-8 pt-8 pb-6">
            <div className="flex items-center justify-center mb-8">
              <TrendingUp className="text-primary w-10 h-10 mr-2" />
              <h1 className="text-3xl font-bold text-white">Dhandha Trading</h1>
            </div>
            {renderForm()}
          </div>
        </motion.div>
      </div>
    </div>
  );
};

export default Auth;
