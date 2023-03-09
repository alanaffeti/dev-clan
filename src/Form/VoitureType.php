<?php

namespace App\Form;

use App\Entity\Voiture;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\CollectionType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Validator\Constraints\NotBlank;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Karser\Recaptcha3Bundle\Form\Recaptcha3Type;
use Karser\Recaptcha3Bundle\Validator\Constraints\Recaptcha3;








class VoitureType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('marque', TextType::class,[
                'constraints' => [
                    new Assert\NotBlank(['message' => 'Veuillez saisir votre marque .']),
            ]])
            ->add('typevoiture', ChoiceType::class, [
                'choices' => [
                    'Camion ' => 'Camion',
                    'Voiture ' => 'Voiture',
                    'moto ' => 'moto',
                ],
                
            ])
            ->add('matricule', TextType::class,[
                'constraints' => [
                    new Assert\NotBlank(['message' => 'Veuillez saisir votre matricule.']),
            ]])
            ->add('modele', TextType::class,[
                'constraints' => [
                    new Assert\NotBlank(['message' => 'Veuillez saisir votre modele de voiture.']),
            ]])
            ->add('datefabrication', DateType::class, [
                'widget' => 'single_text',
               ])
            ->add('typecarburant' , ChoiceType::class, [
                'choices' => [
                    'Diesel ' => 'Diesel',
                    'Essence ' => 'Essence',
                    'Hybride ' => 'Hybride',
                ],
            ])
            ->add('kilometrage', TextType::class,[
                'constraints' => [
                    new Assert\NotBlank(['message' => 'Veuillez saisir votre kilometrage de voiture.']),
            ]])
            ->add('etat', TextType::class,[
                'constraints' => [
                    new Assert\NotBlank(['message' => 'Veuillez saisir votre etat.']),
            ]])
            ->add('contrats', CollectionType::class, [
                'entry_type'=>ContratType::class,
                'entry_options' => ['label' => false],
                
                'allow_add' => true,
                'allow_delete' => true,
                'by_reference' => false,
                'prototype' => true,
                 
            ])
            ->add('save',SubmitType::class)
            ->add('captcha', Recaptcha3Type::class, [
                'constraints' => new Recaptcha3(),
                'action_name' => 'voiture',
                
            ])
            
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Voiture::class,
        ]);
    }
}