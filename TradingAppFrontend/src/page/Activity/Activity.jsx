import React from 'react'
import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table"
import { Avatar, AvatarImage } from 'radix-ui'
import { Button } from '@/components/ui/button'
const Activity = () => {
  return (
    <div className='p-5 lg:p-20'>
    <h1 className='font-bold text-2xl pb-5'>Activity</h1>
    <Table className="border">
      <TableHeader>
        <TableRow>
          <TableHead className="py-5">
            Date & Time
          </TableHead>
          <TableHead>Trading pair</TableHead>
          <TableHead>Buy Price</TableHead>
          <TableHead>Selling Price</TableHead>
          <TableHead>Order Type</TableHead>
          <TableHead className>Profit/Loss</TableHead>
          <TableHead className="text-right">Value</TableHead>

        </TableRow>
      </TableHeader>
      <TableBody>
        {[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1].map((item ,index)=>
        <TableRow key = {index}>
          <TableCell>
            <p>2024/05/31
            </p>
            <p className='text-gray-500'>
            12:39:32

            </p>
          </TableCell>
          <TableCell className="font-medium flex items-center gap-2">
            <Avatar className="z-50">
              <AvatarImage src="https://assets.coingecko.com/coins/images/1/standard/bitcoin.png?1696501400" />
            </Avatar>
            <span>
              Bitcoin
            </span>
          </TableCell>
          <TableCell>8770302578</TableCell>
          <TableCell>913122234713344</TableCell>
          <TableCell>805526885</TableCell>
          <TableCell>$80000.00</TableCell>
          <TableCell className="text-right">345</TableCell>



        </TableRow>
        )}
      </TableBody>
    </Table>
  </div>
  )
}

export default Activity