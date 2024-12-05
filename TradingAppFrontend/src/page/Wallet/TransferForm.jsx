import { Button } from "@/components/ui/button";
import { DialogClose } from "@/components/ui/dialog";
import { Input } from "@/components/ui/input";
import React from "react";

const TransferForm = () => {
  const [formData, setFormData] = React.useState({
    amount: "",
    walletId: "",
    Purpose: "",
  });
  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };
  const handleSubmit = () => {
    console.log(formData);
  };
  return (
    <div className="pt-10 space-y-5">
      <div>
        <h1 className="pb-1">Enter Amount</h1>
        <Input
          name="amount"
          onChange={handleChange}
          value={formData.amount}
          className="py-7"
          placeholder="$9999"
        />
      </div>
      <div>
        <h1 className="pb-1">Wallet ID </h1>
        <Input
          name="walletId"
          onChange={handleChange}
          value={formData.walletId}
          className="py-7"
          placeholder="ABCDEF"
        />
      </div>
      <div>
        <h1 className="pb-1">Purpose</h1>
        <Input
          name="Purpose"
          onChange={handleChange}
          value={formData.Purpose}
          className="py-7"
          placeholder="Gift for your friend"
        />
      </div>
      <DialogClose className="w-full">
        <Button className="w-full py-7" onClick={handleSubmit}>
          SUBMIT
        </Button>
      </DialogClose>
    </div>
  );
};

export default TransferForm;
